package com.example;


import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

class SpringReactiveExamplesApplicationTests {

	SpringReactiveExamplesApplication app = new SpringReactiveExamplesApplication();

	@Test
	void testFluxNames() {

		var nameFlux = app.userNamesFlux();

		StepVerifier.create(nameFlux)
				.expectNext("P", "A", "W", "A", "N", "P", "M", "I", "T")
				.verifyComplete();
	}

	@Test
	void testUserNameCharMono(){

		StepVerifier.create(app.userNameCharMono())
				.expectNext(List.of("P","a","w","a","n"))
				.verifyComplete();
	}

	@Test
	void testUserNameCharFlatMapMany(){

		StepVerifier.create(app.userNameCharFlatMapMany())
				.expectNext("P","a","w","a","n")
				.verifyComplete();

	}

	@Test
	void testTransformExample(){

		StepVerifier.create(app.transformExample())
				//.expectNext("P","A","W","A","N")
				.expectNext("NULL")
				.verifyComplete();
	}

	@Test
	void testConcatExample(){

		StepVerifier.create(app.concatExample())
				.expectNext("A", "B")
				.verifyComplete();
	}

	@Test
	void testConcatWithExample(){

		StepVerifier.create(app.concatWithExample())
				.expectNext("A", "B")
				.verifyComplete();
	}

	@Test
	void testMergeExample(){

		StepVerifier.create(app.mergeExample())
				.expectNext("A", "Y", "B", "Z")
				.verifyComplete();
	}

	@Test
	void testMergeWithExample(){

		StepVerifier.create(app.mergeWithExample())
				.expectNext("A", "Y", "Z")
				.verifyComplete();
	}

	@Test
	void testMergeSequentialExample(){

		StepVerifier.create(app.mergeSequentialExample())
				.expectNext("A", "B", "Y", "Z")
				.verifyComplete();
	}

	@Test
	void testZipExample(){

		StepVerifier.create(app.zipExample())
				.expectNext("AY", "BZ")
				.verifyComplete();
	}

	@Test
	void testZipWithExample(){

		StepVerifier.create(app.zipWithExample())
				.expectNext("AY", "BZ")
				.verifyComplete();
	}
}
