package com.tianyalan.card.repository.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.tianyalan.card.model.Card;

public interface ReactiveCardRepository extends ReactiveMongoRepository<Card, String> { 

}
