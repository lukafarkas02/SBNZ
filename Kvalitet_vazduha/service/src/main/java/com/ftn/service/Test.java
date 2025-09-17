package com.ftn.service;
//import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
//import org.kie.api.runtime.rule.FactHandle;

//import com.ftn.model.Message;
import com.ftn.util.KnowledgeSessionHelper;

import com.ftn.model.PollutantMeasurment;
import com.ftn.model.UserProfile;
import com.ftn.model.UserCategory;
import com.ftn.model.AirQualityStatus;


public class Test{
    public static void main(){
        try{
            // instanciranje
            KieContainer kc = KnowledgeSessionHelper.createRuleBase();
            System.out.println(kc);
            //KieSession kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kc, "k-session-air");

           // Ulazni podaci
            PollutantMeasurment[] primeri = new PollutantMeasurment[]{
                new PollutantMeasurment(10, 15, 12, 20),   // dobar
                new PollutantMeasurment(22, 45, 38, 90),   // umeren
                new PollutantMeasurment(40, 80, 70, 120),  // nezdrav
                new PollutantMeasurment(120, 200, 150, 250), // veoma nezdrav
                new PollutantMeasurment(300, 400, 350, 500), // opasan
                new PollutantMeasurment(12, 55, 20, 180)   // mešoviti slučaj
            };

            int i = 1;
            for (PollutantMeasurment m : primeri) {
                System.out.println("\n=== Primer " + i + " ===");
                i++;

                KieSession kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kc, "k-session-air");

                UserProfile user = new UserProfile(UserCategory.DETE); // npr. DETE
                AirQualityStatus status = new AirQualityStatus();

                // Insert facts
                kSession.insert(m);
                kSession.insert(user);
                kSession.insert(status);

                // Pokretanje pravila
                kSession.fireAllRules();

                // Ispis rezultata
                System.out.println("Ulazni podaci: " + m);
                System.out.println("Kategorija: " + status.getCategory());
                System.out.println("Objašnjenje: " + status.getExplanation());
                System.out.println("Preporuka: " + status.getRecommendation());

                kSession.dispose();
            }

        }catch(Throwable t){
            t.printStackTrace();
        }
    }
}


            