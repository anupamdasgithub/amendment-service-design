package com.bank.amendments.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kie.kogito.event.DataEvent;
import org.kie.kogito.event.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Forwards Kogito runtime events (process instance, user task, variable) to
 * Kafka so the external Data Index / Management Console can consume them.
 *
 * WHY THIS EXISTS
 * ---------------
 * The stock add-on org.kie.kogito.events.spring.KafkaEventPublisher
 * (kogito-addons-springboot-events-process-kafka:1.44.1.Final) is compiled
 * against Spring Kafka 2.x, where KafkaTemplate.send(String, Object) returns
 * org.springframework.util.concurrent.ListenableFuture. Spring Boot 3.3.x
 * ships Spring Kafka 3.2.x, where that method returns
 * java.util.concurrent.CompletableFuture and the old signature is gone, so the
 * stock add-on throws NoSuchMethodError at KafkaEventPublisher.publishToTopic.
 * There is no Spring Kafka version that both keeps the old send() AND runs on
 * Spring 6 / Jakarta, and the Kogito team has stated the community Spring Boot
 * add-ons do not support Spring Boot 3, so a version pin cannot fix it.
 *
 * This class re-implements the same org.kie.kogito.event.EventPublisher SPI
 * that the engine auto-discovers, publishing the same JSON payload to the same
 * topics, but using the Spring Kafka 3.x send() that returns CompletableFuture.
 * The stock add-on must be kept OFF the classpath (removed from pom.xml) so only
 * this publisher runs.
 *
 * The DataEvent objects extend org.kie.kogito.event.AbstractDataEvent and are
 * already CloudEvent-shaped, so a plain Jackson serialization of the event
 * yields the CloudEvent JSON that Data Index expects.
 */
@Component
public class KafkaProcessEventPublisher implements EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaProcessEventPublisher.class);

    // Topic names match the stock add-on defaults and the topics the Data Index
    // consumer groups subscribe to (kogito-data-index-processinstances, etc.).
    private static final String PI_TOPIC = "kogito-processinstances-events";
    private static final String UI_TOPIC = "kogito-usertaskinstances-events";
    private static final String VI_TOPIC = "kogito-variables-events";

    // DataEvent.getType() values used by Kogito 1.44 to classify events.
    private static final String PI_TYPE = "ProcessInstanceEvent";
    private static final String UI_TYPE = "UserTaskInstanceEvent";
    private static final String VI_TYPE = "VariableInstanceEvent";

    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper json;
    private final Environment env;

    // Mirror the stock add-on toggles; default enabled for PI + UI, matching
    // kogito.events.processinstances.enabled / kogito.events.usertasks.enabled.
    private final boolean processInstancesEvents;
    private final boolean userTasksEvents;
    private final boolean variablesEvents;

    public KafkaProcessEventPublisher(
            KafkaTemplate<String, String> kafka,
            ObjectMapper json,
            Environment env,
            @Value("${kogito.events.processinstances.enabled:true}") boolean processInstancesEvents,
            @Value("${kogito.events.usertasks.enabled:true}") boolean userTasksEvents,
            @Value("${kogito.events.variables.enabled:false}") boolean variablesEvents) {
        this.kafka = kafka;
        this.json = json;
        this.env = env;
        this.processInstancesEvents = processInstancesEvents;
        this.userTasksEvents = userTasksEvents;
        this.variablesEvents = variablesEvents;
    }

    @Override
    public void publish(DataEvent<?> event) {
        String type = event.getType();
        if (type == null) {
            return;
        }
        switch (type) {
            case PI_TYPE -> { if (processInstancesEvents) publishToTopic(event, PI_TOPIC); }
            case UI_TYPE -> { if (userTasksEvents)        publishToTopic(event, UI_TOPIC); }
            case VI_TYPE -> { if (variablesEvents)        publishToTopic(event, VI_TOPIC); }
            default -> log.debug("No topic mapping for event type {}", type);
        }
    }

    @Override
    public void publish(Collection<DataEvent<?>> events) {
        for (DataEvent<?> e : events) {
            publish(e);
        }
    }

    private void publishToTopic(DataEvent<?> event, String defaultTopic) {
        try {
            // Match the stock add-on: allow a per-topic override via property
            // "kogito.addon.cloudevents.kafka.<topic>", else use the default.
            String topic = env.getProperty("kogito.addon.cloudevents.kafka." + defaultTopic, defaultTopic);
            String payload = json.writeValueAsString(event);
            kafka.send(topic, payload).whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("Error while publishing event to Kafka topic {} for event {}", topic, event.getType(), ex);
                } else if (log.isDebugEnabled()) {
                    log.debug("Successfully published event {} to topic {}", event.getType(), topic);
                }
            });
        } catch (Exception e) {
            log.error("Error while serializing event to Kafka topic {} for event {}", defaultTopic, event.getType(), e);
        }
    }
}
