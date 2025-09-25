package com.ftn.service; // ili neki paket koji Spring skenira

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ControllerLogger implements CommandLineRunner {

    private final ApplicationContext ctx;

    public ControllerLogger(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== REST Controllers found ===");
        String[] beans = ctx.getBeanNamesForAnnotation(org.springframework.web.bind.annotation.RestController.class);
        for (String bean : beans) {
            System.out.println(bean);
        }
        System.out.println("==============================");
    }
}
