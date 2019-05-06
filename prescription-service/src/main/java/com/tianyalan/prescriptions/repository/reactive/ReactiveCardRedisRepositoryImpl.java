package com.tianyalan.prescriptions.repository.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;

import com.tianyalan.prescriptions.model.Card;

import reactor.core.publisher.Mono;

@Repository
public class ReactiveCardRedisRepositoryImpl implements ReactiveCardRedisRepository {

	@Autowired
	private ReactiveRedisTemplate<String, Card> reactiveRedisTemplate;

	private static final String HASH_NAME = "Card:";

	@Override
	public Mono<Boolean> saveCard(Card card) {
		return reactiveRedisTemplate.opsForValue().set(HASH_NAME + card.getId(), card);
	}

	@Override
	public Mono<Boolean> updateCard(Card card) {
		return reactiveRedisTemplate.opsForValue().set(HASH_NAME + card.getId(), card);
	}

	@Override
	public Mono<Boolean> deleteCard(String cardId) {
		return reactiveRedisTemplate.opsForValue().delete(HASH_NAME + cardId);
	}

	@Override
	public Mono<Card> findCardById(String cardId) {
		return reactiveRedisTemplate.opsForValue().get(HASH_NAME + cardId);
	}
}
