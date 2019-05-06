
package com.tianyalan.medicine;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.tianyalan.medicine.model.Medicine;

@Component
public class InitDatabase {
	@Bean
	CommandLineRunner init(MongoOperations operations) {
		return args -> {
			operations.dropCollection(Medicine.class);

			operations.insert(new Medicine("M_" + UUID.randomUUID().toString(),"Medicine001", "MedicineName001",
					"New Medicine001", 100F));
			operations.insert(new Medicine("M_" + UUID.randomUUID().toString(),"Medicine002", "MedicineName002",
					"New Medicine002", 200F));
			
			operations.findAll(Medicine.class).forEach(medicine -> {
				System.out.println(medicine.toString());
			});
		};
	}
}