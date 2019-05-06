package com.tianyalan.prescriptions.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.tianyalan.prescriptions.model.Medicine;

import reactor.core.publisher.Mono;

@Component
public class ReactiveMedicineClient {
  
    private static final Logger logger = LoggerFactory.getLogger(ReactiveMedicineClient.class);    
    
    public Mono<Medicine> getMedicine(String medicineCode) {    	
    	logger.debug("Get medicine from remote: {}", medicineCode);
    	
    	Mono<Medicine> medicineMono = WebClient.create()
                .get()
                .uri("http://localhost:5555/medicine/v1/medicines/{medicineCode}", medicineCode)
                .retrieve()
                .bodyToMono(Medicine.class).log("getMedicineFromRemote");
    	
        return medicineMono;
    }
}
