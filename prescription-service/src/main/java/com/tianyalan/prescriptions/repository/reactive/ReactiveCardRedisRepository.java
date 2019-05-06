package com.tianyalan.prescriptions.repository.reactive;

import com.tianyalan.prescriptions.model.Card;

import reactor.core.publisher.Mono;

public interface ReactiveCardRedisRepository {
    Mono<Boolean> saveCard(Card card);
    
    Mono<Boolean> updateCard(Card card);
    
    Mono<Boolean> deleteCard(String cardId);
    
    Mono<Card> findCardById(String cardId);
}


