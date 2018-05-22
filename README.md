# REACTIVE PROGRAMMING AND MICROSERVICES

## BOOKS
ebook : Building Reactive Microservices in Java

## ARTICLES
reactive manifesto
http://www.introtorx.com/Content/v1.0.10621.0/12_CombiningSequences.html#Repeat
https://dzone.com/articles/reactor-core-tutorial
https://projectreactor.io/docs/core/release/reference/docs/index.html
https://tech.io/playgrounds/929/reactive-programming-with-reactor-3/Flux
https://github.com/ReactiveX/RxJava/wiki/Backpressure
https://medium.com/@nithinmallya4/processing-streaming-data-with-spring-webflux-ed0fc68a14de

## PROJECTS
reactivex.io -> rxJava 
reactor
vert.x

Reactive Systems are responsive, resilient, elastic, message-driven.

## COLD & HOT STREAMS


## MERGE & MERGEWITH FUNCTION
```
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
```

## ZIP & ZIPWITH FUNCTION
```
@Test
public void learn_zip()
{
Flux<String> titles = Flux.just("Mr.", "Mrs.");
Flux<String> firstNames = Flux.just("John", "Jane");
Flux<String> lastNames = Flux.just("Doe", "Blake");

Flux<String> names = Flux.zip(titles, firstNames, lastNames).map(t->t.getT1()+" "+t.getT2()+" "+t.getT3());

StepVerifier.create(names).expectNext("Mr. John Doe", "Mrs. Jane Blake").verifyComplete();
}

@Test
public void learn_zipWith()
{
Flux<String> titles = Flux.just("Mr.", "Mrs.");
Flux<String> firstNames = Flux.just("John", "Jane");
Flux<String> lastNames = Flux.just("Doe", "Blake");

Flux<String> names = titles.zipWith(firstNames, (t,f)->t+" "+f).zipWith(lastNames, (f,l)->f+" "+l);

StepVerifier.create(names)
.expectNext("Mr. John Doe", "Mrs. Jane Blake")
.verifyComplete();
}
```
