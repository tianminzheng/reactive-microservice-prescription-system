package com.tianyalan.prescriptions.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianyalan.prescriptions.model.Prescription;
import com.tianyalan.prescriptions.services.PrescriptionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PrescriptionController {
	
    @Autowired
    private PrescriptionService prescriptionService;
	
    @PostMapping("/v1/prescriptions/{cardId}/{medicineCode}")
	public Mono<Prescription> savePrescription( @PathVariable("cardId") String cardId,
            @PathVariable("medicineCode") String medicineCode) {
		Mono<Prescription> prescription = prescriptionService.addPrescription(cardId, medicineCode);		
		
		return prescription;
	}
	
    @GetMapping("/v1/prescriptions/{id}")
    public Mono<Prescription> getPrescription(@PathVariable String id) {
		Mono<Prescription> prescription = prescriptionService.getPrescriptionById(id);
		
    	return prescription;
    }

    @GetMapping("/v1/prescriptions")
	public Flux<Prescription> getPrescriptionList() {
		Flux<Prescription> prescriptionServices = prescriptionService.getPrescriptions();
		
		return prescriptionServices;
	}	
}
