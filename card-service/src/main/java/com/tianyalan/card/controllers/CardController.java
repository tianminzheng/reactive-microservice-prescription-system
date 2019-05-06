package com.tianyalan.card.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tianyalan.card.model.Card;
import com.tianyalan.card.services.CardService;

import reactor.core.publisher.Mono;

@RestController
public class CardController {
	@Autowired
	private CardService cardService;

	private static final Logger logger = LoggerFactory.getLogger(CardController.class);

	@GetMapping("/v1/cards/{cardId}")
	public Mono<Card> getCard(@PathVariable("cardId") String cardId) {		
		 Mono<Card> card = cardService.getCardById(cardId);
		 return card;
	}

	@PutMapping("/v1/cards")
	public Mono<Void> updateCard(@RequestBody Card card) {
		return cardService.updateCard(card);
	}

	@PostMapping("/v1/cards")
	public Mono<Void> saveCard(@RequestBody Card card) {
		return cardService.updateCard(card);
	}

	@DeleteMapping("/v1/cards/{cardId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> deleteCard(@PathVariable("cardId") String cardId) {
		Card card = new Card();
		card.setId(cardId);

		return cardService.deleteCard(card);
	}
}
