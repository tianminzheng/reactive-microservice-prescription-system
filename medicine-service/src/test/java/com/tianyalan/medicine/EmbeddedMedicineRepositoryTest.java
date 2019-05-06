package com.tianyalan.medicine;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.tianyalan.medicine.model.Medicine;
import com.tianyalan.medicine.repository.reactive.ReactiveMedicineRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@RunWith(SpringRunner.class)
@DataMongoTest
public class EmbeddedMedicineRepositoryTest {

	@Autowired
	ReactiveMedicineRepository repository;

	@Autowired
	MongoOperations operations;
	
	@Before
	public void setUp() {
		operations.dropCollection(Medicine.class);

		operations.insert(new Medicine("Medicine" + UUID.randomUUID().toString(),"Book001", "Microservie Practices",
				"New Book For Microservie By Tianyalan", 100F));
		operations.insert(new Medicine("Medicine" + UUID.randomUUID().toString(),"Book002", "Microservie Design",
				"Another New Book For Microservie By Tianyalan", 200F));
		
		operations.findAll(Medicine.class).forEach(medicine -> {
			System.out.println(medicine.toString());
		});
	}
	
	@Test
	public void testGetByMedicineCode() {
		Mono<Medicine> medicine = repository.getByMedicineCode("Book001");

		StepVerifier.create(medicine)
			.expectNextMatches(results -> {
					assertThat(results.getMedicineCode()).isEqualTo("Book001");
					assertThat(results.getMedicineName()).isEqualTo("Microservie Practices");
					return true;
			});
	}
}
