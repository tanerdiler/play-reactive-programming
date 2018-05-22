package com.webflux.learning.learning1;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ReactiveLearning
{
	@Test
	public void learn_empty()
	{
		Mono<String> emptyMono = Mono.empty();
		StepVerifier.create(emptyMono).verifyComplete();

		Flux<String> emptyFlux = Flux.empty();
		StepVerifier.create(emptyFlux).verifyComplete();
	}

	@Test
	public void learn_initialized()
	{
		Mono<String> justMono = Mono.just("Taner");
		StepVerifier.create(justMono).expectNext("Taner").verifyComplete();

		Flux<String> justFlux = Flux.just("John", "Michael");
		StepVerifier.create(justFlux).expectNext("John").expectNext("Michael").verifyComplete();

		Flux<String> fluxFromIterable = Flux.fromIterable(Arrays.asList("Michale", "John"));
		StepVerifier.create(fluxFromIterable).expectNext("Michale").expectNext("John").verifyComplete();

		Flux<Integer> fluxRange = Flux.range(0, 5);
		StepVerifier.create(fluxRange).expectNext(0, 1, 2, 3, 4).verifyComplete();

	}

	@Test
	public void learn_repeat() // extension method
	{
		Flux<Integer> rangedFlux = Flux.range(0, 3).repeat(3);
		StepVerifier.create(rangedFlux)
				.expectNext(0, 1, 2)
				.expectNext(0, 1, 2)
				.expectNext(0, 1, 2).verifyComplete();
	}

	@Test
	public void learn_concatWith() // extension method
	{
		Flux<Integer> rangedFlux1 = Flux.range(0, 3);
		Flux<Integer> rangedFlux2 = Flux.range(3, 3);
		Flux<Integer> combinedFlux = rangedFlux1.concatWith(rangedFlux2);

		StepVerifier.create(combinedFlux)
				.expectNext(0, 1, 2, 3, 4, 5)
				.verifyComplete();
	}

	@Test
	public void learn_zip() // extension method
	{
		Flux<String> titles = Flux.just("Mr.", "Mrs.");
		Flux<String> firstNames = Flux.just("John", "Jane");
		Flux<String> lastNames = Flux.just("Doe", "Blake");

		Flux<String> names = Flux.zip(titles, firstNames, lastNames).map(t->t.getT1()+" "+t.getT2()+" "+t.getT3());

		StepVerifier.create(names)
				.expectNext("Mr. John Doe", "Mrs. Jane Blake")
				.verifyComplete();
	}

	@Test
	public void learn_zipWith() // extension method
	{
		Flux<String> titles = Flux.just("Mr.", "Mrs.");
		Flux<String> firstNames = Flux.just("John", "Jane");
		Flux<String> lastNames = Flux.just("Doe", "Blake");

		Flux<String> names = titles.zipWith(firstNames, (t,f)->t+" "+f).zipWith(lastNames, (f,l)->f+" "+l);

		StepVerifier.create(names)
				.expectNext("Mr. John Doe", "Mrs. Jane Blake")
				.verifyComplete();
	}

	@Test
	public void learn_interval() // extension method
	{
		Flux<String> titles = Flux.just("Mr.", "Mrs.");
		Flux<String> firstNames = Flux.just("John", "Jane");
		Flux<String> lastNames = Flux.just("Doe", "Blake");

		Flux<String> names = Flux.zip(titles, firstNames, lastNames).map(t->t.getT1()+" "+t.getT2()+" "+t.getT3());
		Flux<Long> interval = Flux.interval(Duration.ofMillis(250));
		Flux<String> firstNamesWithDelay = names.zipWith(interval, (s,l)->s);

		StepVerifier.create(firstNamesWithDelay)
				.expectNext("Mr. John Doe", "Mrs. Jane Blake")
				.verifyComplete();
	}

	@Test
	public void learn_startWith()
	{
		Flux<Integer> rangedFlux1 = Flux.range(0, 3);
		Flux<Integer> fluxWithStart = rangedFlux1.startWith(-1, 10, 2);
		StepVerifier.create(fluxWithStart).expectNext(-1, 10, 2, 0, 1, 2).verifyComplete();
	}

	@Test
	public void learn_merge()
	{
		Flux<String> firstnames = Flux.just("Ahmet","Ali", "Veli");
		Flux<String> lastnames = Flux.just("Gitti","Geldi", "Oturdu");
		Flux<String> fList = firstnames.zipWith(Flux.interval(Duration.ofMillis(250)), (f,s)->f);
		Flux<String> lList = lastnames.zipWith(Flux.interval(Duration.ofMillis(150)), (f,s)->f);
		Flux<String> mergedList = Flux.merge(lList, lList);
		StepVerifier.create(mergedList).expectNext("Gitti","Ahmet", "Geldi","Oturdu", "Ali", "Veli").verifyComplete();
	}

	@Test
	public void learn_mergeWith()
	{
		Flux<String> firstnames = Flux.just("Ahmet","Ali", "Veli");
		Flux<String> lastnames = Flux.just("Gitti","Geldi", "Oturdu");
		Flux<String> fList = firstnames.zipWith(Flux.interval(Duration.ofMillis(250)), (f,s)->f);
		Flux<String> lList = lastnames.zipWith(Flux.interval(Duration.ofMillis(150)), (f,s)->f);
		Flux<String> mergedList = fList.mergeWith(lList);
		StepVerifier.create(mergedList).expectNext("Gitti","Ahmet", "Geldi","Oturdu", "Ali", "Veli").verifyComplete();
	}
}
