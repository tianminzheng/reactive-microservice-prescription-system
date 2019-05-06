
package com.tianyalan.card;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.tianyalan.card.model.Card;

@Component
public class InitDatabase {
	@Bean
	CommandLineRunner init(MongoOperations operations) {
		return args -> {
			operations.dropCollection(Card.class);

			operations.insert(new Card("C_" + UUID.randomUUID().toString(),"tianyalan1", "tianmin zheng1"));
			operations.insert(new Card("C_" + UUID.randomUUID().toString(),"tianyalan2", "tianmin zheng2"));
			
			operations.findAll(Card.class).forEach(card -> {
				System.out.println(card.getId());
			});
		};
	}
}