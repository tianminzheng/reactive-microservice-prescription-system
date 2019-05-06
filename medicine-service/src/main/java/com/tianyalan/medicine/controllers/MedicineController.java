package com.tianyalan.medicine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tianyalan.medicine.model.Medicine;
import com.tianyalan.medicine.services.MedicineService;

import reactor.core.publisher.Mono;

@RestController
public class MedicineController {

	@Autowired
	MedicineService medicineService;
    
    @DeleteMapping("/v1/medicines/{medicineId}")
    public Mono<Void> deleteMedicine(@PathVariable String medicineId) {		
		
		Mono<Void> result = medicineService.deleteMedicineById(medicineId);
    	return result;
    }
    
    @GetMapping("/v1/medicines/{medicineCode}")
    public Mono<Medicine> getMedicine(@PathVariable String medicineCode) {		
    	
		Mono<Medicine> medicine = medicineService.getMedicineByCode(medicineCode);
    	return medicine;
    }

}
