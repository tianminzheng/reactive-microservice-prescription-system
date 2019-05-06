package com.tianyalan.medicine;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ReactorTest {

//	Flux<String> helloWorld = Flux.just("Hello", "World");

	@Test
	public void testStepVerifier() {
		StepVerifier.create(helloWorld)
			.expectNext("Hello")
			.expectNext("World")
			.expectComplete()
			.verify();
	}

	Flux<String> helloWorld = Flux.just("Hello", "World");
	
	@Test
	public void testStepVerifierappendBoomError() {
		Flux<String> helloWorldWithBoom 
			= helloWorld.concatWith(Mono.error(new IllegalArgumentException("boom")));

		StepVerifier.create(helloWorldWithBoom)
			.expectNext("Hello")
			.expectNext("World")
			.expectErrorMessage("boom")
			.verify();
	}
	
	
}
