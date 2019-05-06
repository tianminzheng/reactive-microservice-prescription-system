package com.tianyalan.prescriptions.repository.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.tianyalan.prescriptions.model.Prescription;

public interface ReactivePrescriptionRepository extends ReactiveMongoRepository<Prescription, String> {

}
