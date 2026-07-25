/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.drools.bpmn2;

import java.util.Optional;
import java.util.function.Function;
import org.kie.kogito.process.Process;
import org.kie.kogito.addon.cloudevents.spring.SpringMessageConsumer;
import org.kie.kogito.event.EventReceiver;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@org.springframework.stereotype.Component()
public class Amendment_requestMessageConsumer_26_27 extends SpringMessageConsumer<Amendment_requestModel, String> {

    @Autowired
    @Qualifier("amendment_request")
    Process<Amendment_requestModel> process;

    @Autowired
    EventReceiver eventReceiver;

    @PostConstruct
    void init() {
        init(process, "AmendmentCancelled", String.class, eventReceiver);
    }

    private Amendment_requestModel eventToModel(String event) {
        Amendment_requestModel model = new Amendment_requestModel();
        if (event != null) {
            model.setRequestId(event);
        }
        return model;
    }

    @Override()
    protected Optional<Function<String, Amendment_requestModel>> getModelConverter() {
        return Optional.of(this::eventToModel);
    }
}
