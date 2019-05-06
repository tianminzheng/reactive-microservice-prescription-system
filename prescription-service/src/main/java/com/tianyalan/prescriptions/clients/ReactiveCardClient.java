package com.tianyalan.prescriptions.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.tianyalan.prescriptions.model.Card;
import com.tianyalan.prescriptions.repository.reactive.ReactiveCardRedisRepository;

import reactor.core.publisher.Mono;

@Component
public class ReactiveCardClient {

    private static final Logger logger = LoggerFactory.getLogger(ReactiveCardClient.class);
    
    @Autowired
    ReactiveCardRedisRepository cardReactiveRedisRepository;
    
    private Mono<Card> getCardFromCache(String cardId) {
    	logger.info("Get card from redis: {}", cardId);
    	
        return cardReactiveRedisRepository.findCardById(cardId);
    }
    
    private void putCardIntoCache(Card card) {    	
    	logger.info("Put card into redis: {}", card.getId());
		
		cardReactiveRedisRepository.saveCard(card).subscribe();
    }
    
    public Mono<Card> getCard(String cardId){   
    	logger.debug("Get card from remote: {}", cardId); 	
    	
    	Mono<Card> cardMonoFromCache = getCardFromCache(cardId); 
    	
    	Mono<Card> cardMono = cardMonoFromCache.switchIfEmpty(getCardFromRemote(cardId));    	
        
        return cardMono;
    }
    
    private Mono<Card> getCardFromRemote(String cardId) {
    	Mono<Card> cardMonoFromRemote = WebClient.create()
    			.get()
    			.uri("http://localhost:5555/card/v1/cards/{cardId}", cardId) 
    			.retrieve()
    			.bodyToMono(Card.class).log("getCardFromRemote");    	

    	cardMonoFromRemote.flatMap(card -> {
    		if(card != null) {
    			putCardIntoCache(card);
    		}
    		
    		return Mono.just(card);
    	});
    	
    	return cardMonoFromRemote;
    }    
}
