package com.tianyalan.medicine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import com.tianyalan.medicine.model.Medicine;
import com.tianyalan.medicine.repository.reactive.ReactiveMedicineRepository;

import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Mono;

@Service
public class MedicineService {
	
	@Autowired
	private ReactiveMedicineRepository reactiveMedicineRepository;
		
	@Autowired
    private Tracer tracer;
	
	@Autowired
	private MeterRegistry meterRegistry;	
	
	public Mono<Medicine> getMedicineByCode(String medicineCode) {
				
		Span newSpan = tracer.createSpan("getMedicineByCode");
		
		try {			
			return reactiveMedicineRepository.getByMedicineCode(medicineCode);
        }
        finally{
          newSpan.tag("peer.service", "database");
          newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
          tracer.close(newSpan);
        }
	}
	
	public Mono<Void> deleteMedicineById(String id) {
		Mono<Void> deleteMedicine = reactiveMedicineRepository.deleteById(id);
		
		Mono<Void> countDeletedMedicine = Mono.fromRunnable(() -> {
			meterRegistry.summary("medicines.deleted.count").record(1);
		});
		
		return Mono.when(deleteMedicine, countDeletedMedicine);
	}	
}
