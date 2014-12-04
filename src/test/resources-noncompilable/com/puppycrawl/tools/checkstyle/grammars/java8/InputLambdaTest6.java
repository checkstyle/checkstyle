package com.puppycrawl.tools.checkstyle.grammars.java8;

import java.util.Arrays;
import java.util.List;

public class InputLambdaTest6 {

	public void doSomething() {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
		
		numbers.forEach((value) -> System.out.println(value));
	}
}