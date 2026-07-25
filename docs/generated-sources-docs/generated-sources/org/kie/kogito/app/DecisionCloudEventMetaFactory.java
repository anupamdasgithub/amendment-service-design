package org.kie.kogito.app;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.kie.kogito.config.ConfigBean;
import org.kie.kogito.event.cloudevents.CloudEventMeta;
import org.kie.kogito.event.EventKind;

@org.springframework.context.annotation.Configuration()
public class DecisionCloudEventMetaFactory {

    @org.springframework.beans.factory.annotation.Autowired()
    ConfigBean config;

    private CloudEventMeta buildCloudEventMeta(String type, String sourceSuffix, EventKind kind) {
        String source = kind == EventKind.PRODUCED ? Stream.of(config.getServiceUrl(), sourceSuffix).filter(s -> s != null && !s.isEmpty()).collect(Collectors.joining("/")) : "";
        return new CloudEventMeta(type, source, kind);
    }

    @org.springframework.context.annotation.Bean()
    public CloudEventMeta buildCloudEventMeta_CONSUMED_DecisionRequest() {
        return new CloudEventMeta("DecisionRequest", "", EventKind.CONSUMED);
    }

    @org.springframework.context.annotation.Bean()
    public CloudEventMeta buildCloudEventMeta_PRODUCED_DecisionResponseError_UnknownModel() {
        String source = Optional.of(config.getServiceUrl()).filter(s -> s != null && !s.isEmpty()).orElse("__UNKNOWN_SOURCE__");
        return new CloudEventMeta("DecisionResponseError", source, EventKind.PRODUCED);
    }

    @org.springframework.context.annotation.Bean()
    public CloudEventMeta buildCloudEventMeta_PRODUCED_PRODUCED_DecisionResponseError_AmendmentAdmissibility() {
        return buildCloudEventMeta("DecisionResponseError", "AmendmentAdmissibility", org.kie.kogito.event.EventKind.PRODUCED);
    }

    @org.springframework.context.annotation.Bean()
    public CloudEventMeta buildCloudEventMeta_PRODUCED_PRODUCED_DecisionResponse_AmendmentSequencing() {
        return buildCloudEventMeta("DecisionResponse", "AmendmentSequencing", org.kie.kogito.event.EventKind.PRODUCED);
    }

    @org.springframework.context.annotation.Bean()
    public CloudEventMeta buildCloudEventMeta_PRODUCED_PRODUCED_DecisionResponseError_AmendmentSequencing() {
        return buildCloudEventMeta("DecisionResponseError", "AmendmentSequencing", org.kie.kogito.event.EventKind.PRODUCED);
    }

    @org.springframework.context.annotation.Bean()
    public CloudEventMeta buildCloudEventMeta_PRODUCED_PRODUCED_DecisionResponse_JointToSoleEligibility() {
        return buildCloudEventMeta("DecisionResponse", "JointToSoleEligibility", org.kie.kogito.event.EventKind.PRODUCED);
    }

    @org.springframework.context.annotation.Bean()
    public CloudEventMeta buildCloudEventMeta_PRODUCED_PRODUCED_DecisionResponseError_JointToSoleEligibility() {
        return buildCloudEventMeta("DecisionResponseError", "JointToSoleEligibility", org.kie.kogito.event.EventKind.PRODUCED);
    }

    @org.springframework.context.annotation.Bean()
    public CloudEventMeta buildCloudEventMeta_PRODUCED_PRODUCED_DecisionResponseFull_AmendmentSequencing() {
        return buildCloudEventMeta("DecisionResponseFull", "AmendmentSequencing", org.kie.kogito.event.EventKind.PRODUCED);
    }

    @org.springframework.context.annotation.Bean()
    public CloudEventMeta buildCloudEventMeta_PRODUCED_PRODUCED_DecisionResponseFull_JointToSoleEligibility() {
        return buildCloudEventMeta("DecisionResponseFull", "JointToSoleEligibility", org.kie.kogito.event.EventKind.PRODUCED);
    }

    @org.springframework.context.annotation.Bean()
    public CloudEventMeta buildCloudEventMeta_PRODUCED_PRODUCED_DecisionResponse_AmendmentAdmissibility() {
        return buildCloudEventMeta("DecisionResponse", "AmendmentAdmissibility", org.kie.kogito.event.EventKind.PRODUCED);
    }

    @org.springframework.context.annotation.Bean()
    public CloudEventMeta buildCloudEventMeta_PRODUCED_PRODUCED_DecisionResponseFull_AmendmentAdmissibility() {
        return buildCloudEventMeta("DecisionResponseFull", "AmendmentAdmissibility", org.kie.kogito.event.EventKind.PRODUCED);
    }
}
