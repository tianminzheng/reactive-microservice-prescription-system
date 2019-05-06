package com.tianyalan.card.events.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.reactive.FluxSender;
import org.springframework.cloud.stream.reactive.StreamEmitter;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.tianyalan.card.event.models.CardChangedEvent;
import com.tianyalan.card.model.Card;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

@Component
public class ReactiveCardChangeSource {

    private static final Logger logger = LoggerFactory.getLogger(ReactiveCardChangeSource.class);
  
	private FluxSink<Message<CardChangedEvent>> eventSink;
	private Flux<Message<CardChangedEvent>> flux;
	
	public ReactiveCardChangeSource() {
		this.flux = Flux.<Message<CardChangedEvent>>create(sink -> this.eventSink = sink).publish().autoConnect();
	}
	
    private Mono<Void> publishReactiveCardChange(String operation, Card card){
    	logger.debug("Sending message for Card Id: {}", card.getId());
    	
        CardChangedEvent originalevent =  new CardChangedEvent(
        		CardChangedEvent.class.getTypeName(),
        		operation,
                card);

        Mono<CardChangedEvent> monoEvent = Mono.just(originalevent);
        
        return monoEvent.map(event -> eventSink.next(MessageBuilder.withPayload(event).build())).then();
    }
    
    @StreamEmitter
	public void emit(@Output(Source.OUTPUT) FluxSender output) {
		output.send(this.flux);
	}
    
    public Mono<Void> publishReactiveCardUpdatedEvent(Card card) {
    	return publishReactiveCardChange("UPDATE", card);
    }
    
    public Mono<Void> publishReactiveCardDeletedEvent(Card card) {
    	return publishReactiveCardChange("DELETE", card);
    }
}
