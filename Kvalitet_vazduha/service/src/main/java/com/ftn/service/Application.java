package com.ftn.service;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(
	scanBasePackages = "com.ftn",
	exclude = { 
    	org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class 
})
@EntityScan(basePackages = "com.ftn.model") // <-- ovde skeniramo JPA entitete
@EnableJpaRepositories(basePackages = "com.ftn.service") // <-- ovde skeniramo repozitorijume
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
   
    @Bean
	public KieContainer kieContainer()
		{
			KieServices ks = KieServices.Factory.get();
			KieContainer kContainer = ks
					.newKieContainer(ks.newReleaseId("com.ftn.sbnz", "kjar", "0.0.1-SNAPSHOT"));
			KieScanner kScanner = ks.newKieScanner(kContainer);
			kScanner.start(10_000);
			return kContainer;
		}
		
	@Override
	public void run(String... args) throws Exception 
	{
	
		// pokretati odvojeno svaku main klasu
		// Test.main();
		
		//GlobalExample.main();
		
		//ModifyExample.main();
		//AgendaGroupExample.main();
		//DeleteExample.main();
		
	}

}