package com.tianyalan.prescriptions;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.reactive.function.client.WebClient;

import com.tianyalan.prescriptions.model.Card;


@SpringCloudApplication
@EnableBinding(Sink.class)
public class PrescriptionApplication {

	@Bean
	@LoadBalanced
	public WebClient.Builder loadBalancedWebClientBuilder() {
		return WebClient.builder();
	}
	
	@Bean
	public ReactiveRedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory();
	}

//	@Bean
//	ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
//		return new ReactiveRedisTemplate<>(new LettuceConnectionFactory(), RedisSerializationContext.string());
//	}
//
//	@Bean
//	ReactiveRedisTemplate<String, Card> redisOperations() {
//		Jackson2JsonRedisSerializer<Card> serializer = new Jackson2JsonRedisSerializer<>(Card.class);
//
//		RedisSerializationContext.RedisSerializationContextBuilder<String, Card> builder = RedisSerializationContext
//				.newSerializationContext(new StringRedisSerializer());
//
//		RedisSerializationContext<String, Card> context = builder.value(serializer).build();
//
//		return new ReactiveRedisTemplate<>(new LettuceConnectionFactory(), context);
//	}
	
	@Bean
	ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
		return new ReactiveRedisTemplate<>(factory, RedisSerializationContext.string());
	}
	
	@Bean
	ReactiveRedisTemplate<String, Card> redisOperations(ReactiveRedisConnectionFactory factory) {
		Jackson2JsonRedisSerializer<Card> serializer = new Jackson2JsonRedisSerializer<>(Card.class);

		RedisSerializationContext.RedisSerializationContextBuilder<String, Card> builder = RedisSerializationContext
				.newSerializationContext(new StringRedisSerializer());

		RedisSerializationContext<String, Card> context = builder.value(serializer).build();

		return new ReactiveRedisTemplate<>(factory, context);
	}

	public static void main(String[] args) {
		SpringApplication.run(PrescriptionApplication.class, args);
	}
}
