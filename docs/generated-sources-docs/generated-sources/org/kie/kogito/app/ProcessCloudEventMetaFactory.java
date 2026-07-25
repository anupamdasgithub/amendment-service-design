package org.kie.kogito.app;

import org.kie.kogito.event.cloudevents.CloudEventMeta;

@org.springframework.context.annotation.Configuration()
public class ProcessCloudEventMetaFactory {

    @org.springframework.context.annotation.Bean()
    public CloudEventMeta buildCloudEventMeta_CONSUMED_AdditionalVerificationRequired() {
        return new CloudEventMeta("AdditionalVerificationRequired", "", org.kie.kogito.event.EventKind.CONSUMED);
    }

    @org.springframework.context.annotation.Bean()
    public CloudEventMeta buildCloudEventMeta_CONSUMED_ConsentDisputed() {
        return new CloudEventMeta("ConsentDisputed", "", org.kie.kogito.event.EventKind.CONSUMED);
    }

    @org.springframework.context.annotation.Bean()
    public CloudEventMeta buildCloudEventMeta_CONSUMED_AmendmentCancelled() {
        return new CloudEventMeta("AmendmentCancelled", "", org.kie.kogito.event.EventKind.CONSUMED);
    }

    @org.springframework.context.annotation.Bean()
    public CloudEventMeta buildCloudEventMeta_CONSUMED_ExtraDocumentSubmitted() {
        return new CloudEventMeta("ExtraDocumentSubmitted", "", org.kie.kogito.event.EventKind.CONSUMED);
    }
}
