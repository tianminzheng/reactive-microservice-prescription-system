package com.tianyalan.prescriptions.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tianyalan.prescriptions.clients.ReactiveCardClient;
import com.tianyalan.prescriptions.clients.ReactiveMedicineClient;
import com.tianyalan.prescriptions.model.Card;
import com.tianyalan.prescriptions.model.Prescription;
import com.tianyalan.prescriptions.model.Medicine;
import com.tianyalan.prescriptions.repository.reactive.ReactivePrescriptionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PrescriptionService {

	@Autowired
	private ReactivePrescriptionRepository reactivePrescriptionRepository;

	@Autowired
	private ReactiveMedicineClient medicineClient;

	@Autowired
	private ReactiveCardClient cardClient;	

	private static final Logger logger = LoggerFactory.getLogger(PrescriptionService.class);

	@HystrixCommand
	private Mono<Card> getCard(String cardId) {

		return cardClient.getCard(cardId).log("getCard");
	}

	@HystrixCommand
	private Mono<Medicine> getMedicine(String medicineCode) {

		return medicineClient.getMedicine(medicineCode).log("getMedicine");
	}
	
	public Mono<Prescription> addPrescription(String cardId, String medicineCode) {		
		logger.debug("add prescription with card: {} and medicine: {}", cardId, medicineCode);
		
		Prescription prescription = new Prescription();		
		prescription.setId("P_" + UUID.randomUUID().toString());

		Mono<Medicine> medicine = getMedicine(medicineCode); 
		medicine.flatMap( m -> {
			if(m != null) {
				logger.debug("get medicine: {} is successful", medicineCode);
				
				prescription.setMedicineCode(medicineCode);
				prescription.setMedicineName(m.getMedicineName());
			}
			
			return Mono.just(m);
		}).block();
		
		Mono<Card> card = getCard(cardId);
		card.flatMap( c -> {
			if(c != null) {
				logger.debug("get card: {} is successful", cardId);
				
				prescription.setCardId(cardId);
				prescription.setCardName(c.getCardName());
			}
			
			return Mono.just(c);
		}).block();
		
		if(prescription.getCardId() == null || prescription.getMedicineCode() == null) {
			return Mono.empty();
		}
		
		prescription.setCreateTime(new Date());

		Mono<Prescription> savedPrescription = reactivePrescriptionRepository.save(prescription).log("savePrescription");

		return savedPrescription;
	}
	
	@HystrixCommand(fallbackMethod = "getPrescriptionsFallback")
	public Flux<Prescription> getPrescriptions() {
		return reactivePrescriptionRepository.findAll();
	}

	@SuppressWarnings("unused")	
	private Flux<Prescription> getPrescriptionsFallback() {
		List<Prescription> fallbackList = new ArrayList<>();

		Prescription prescription = new Prescription();
		prescription.setId("InvalidPrescriptionId");
		prescription.setCardId("InvalidCardId" );
		prescription.setMedicineCode("InvalidMedicineCode");
		prescription.setCreateTime(new Date());
		fallbackList.add(prescription);
		
		return Flux.fromIterable(fallbackList);
	}

	@HystrixCommand(fallbackMethod = "getPrescriptionFallback")
	public Mono<Prescription> getPrescriptionById(String id) {
		return reactivePrescriptionRepository.findById(id);
	}
	
	@SuppressWarnings("unused")	
	private Mono<Prescription> getPrescriptionFallback(String id) {		
		Prescription prescription = new Prescription();
		prescription.setId(id);
		prescription.setCardId("InvalidCardId" );
		prescription.setMedicineCode("InvalidMedicineCode");
		prescription.setCreateTime(new Date());
		
		return Mono.just(prescription);
	}
}
