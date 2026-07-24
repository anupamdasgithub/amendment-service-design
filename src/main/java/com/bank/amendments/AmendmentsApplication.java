package com.bank.amendments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Kogito generates its beans into org.kie.kogito.app, outside this
// application's package, so that package must be scanned explicitly.
@SpringBootApplication(scanBasePackages = {"com.bank.amendments", "org.kie.kogito", "org.drools.bpmn2"})
public class AmendmentsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AmendmentsApplication.class, args);
    }
}
