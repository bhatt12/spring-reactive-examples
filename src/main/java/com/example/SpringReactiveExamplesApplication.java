package com.example;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class SpringReactiveExamplesApplication {

	public static void main(String[] args) {
		SpringReactiveExamplesApplication app = new SpringReactiveExamplesApplication();

		app.userNamesFlux().subscribe(name->System.out.println(name));
		app.userNameMono().subscribe(name->System.out.println("Mono : Name " + name));
		app.userNameCharMono().subscribe(output->System.out.println(output));
	}

	public Flux<String> transformExample(){

		Function<Flux<String>, Flux<String>> toUpperAndLengthFunction = name ->
				name.map(String::toUpperCase)
						.filter(a->a.length() > 4);


		return Flux.fromIterable(List.of("Pawan", "Anil"))
				.transform(toUpperAndLengthFunction)
				.flatMap(this::getChars)
			//	.defaultIfEmpty("NULL")
				.switchIfEmpty(Flux.just("NULL"));
	}

	public Flux<String> userNamesFlux(){
		return Flux.fromIterable(List.of("Pawan", "Pmit", "Ayushi"))
				.map(String::toUpperCase)
				.filter(s->s.startsWith("P"))
				.flatMap(s->getChars(s))
				.concatMap(s->getChars(s))
				.log();
	}

	public Flux<String> getChars(String a){
		return Flux.fromArray(a.split(""));
	}

	public Mono<String> userNameMono(){
		return Mono.just("Pawan")
				.log();
	}

	public Mono<List<String>> userNameCharMono(){
		return Mono.just("Pawan")
				.flatMap(this::getCharsMono)
				.log();
	}

	public Flux<String> userNameCharFlatMapMany(){
		return Mono.just("Pawan")
				.flatMapMany(this::getChars)
				.log();
	}

	private Mono<List<String>> getCharsMono(String s) {

		return Mono.just(List.of(s.split("")));

	}

	public Flux<String> concatExample(){

		var flux1 = Flux.just("A");
		var flux2 = Flux.just("B");

		return Flux.concat(flux1, flux2);
	}

	public Flux<String> concatWithExample(){

		var mono1 = Mono.just("A");
		var flux1 = Flux.just("B");

		return mono1.concatWith(flux1);
	}

	public Flux<String> mergeExample(){

		var flux1 = Flux.just("A", "B")
				.delayElements(Duration.ofMillis(100));
		var flux2 = Flux.just("Y", "Z")
				.delayElements(Duration.ofMillis(120));;

		return Flux.merge(flux1, flux2).log();
	}

	public Flux<String> mergeWithExample(){

		var mono1 = Mono.just("A")
				.delayElement(Duration.ofMillis(100));
		var flux2 = Flux.just("Y", "Z")
				.delayElements(Duration.ofMillis(120));;

		return mono1.mergeWith(flux2).log();
	}

	public Flux<String> mergeSequentialExample(){

		var flux1 = Flux.just("A", "B")
				.delayElements(Duration.ofMillis(100));
		var flux2 = Flux.just("Y", "Z")
				.delayElements(Duration.ofMillis(120));;

		return Flux.mergeSequential(flux1, flux2).log();
	}

	public Flux<String> zipExample(){

		var flux1 = Flux.just("A","B");
		var flux2 = Flux.just("Y", "Z");;

		return Flux.zip(flux1, flux2, (first, second)-> first + second).log();
	}

	public Flux<String> zipWithExample(){

		var flux1 = Flux.just("A", "B");
		var flux2 = Flux.just("Y", "Z");;

		return flux1.zipWith(flux2, (f1, f2)->f1+f2).log();
	}
}
