package com.tianyalan.card.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyalan.card.events.source.ReactiveCardChangeSource;
import com.tianyalan.card.model.Card;
import com.tianyalan.card.repository.reactive.ReactiveCardRepository;

import reactor.core.publisher.Mono;

@Service
public class CardService {
    @Autowired
    private ReactiveCardRepository reactiveCardRepository;

    @Autowired
    private ReactiveCardChangeSource reactiveCardChangeSource;

    public Mono<Card> getCardById(String cardId) {
        return reactiveCardRepository.findById(cardId);
    }

    public Mono<Card> saveCard(Card card){
    	Mono<Card> saveCard = reactiveCardRepository.save(card);
    	
    	return saveCard;
    }

    public Mono<Void> updateCard(Card card){
    	Mono<Card> saveCard = reactiveCardRepository.save(card);
    	   	
    	Mono<Void> updatedEvent = reactiveCardChangeSource.publishReactiveCardUpdatedEvent(card);
    	
    	return Mono.when(saveCard, updatedEvent);
    }

    public Mono<Void> deleteCard(Card card){
    	Mono<Void> deleteCard = reactiveCardRepository.delete(card);
   	
    	Mono<Void> deletedEvent = reactiveCardChangeSource.publishReactiveCardDeletedEvent(card);
    	
    	return Mono.when(deleteCard, deletedEvent);
    }
}
