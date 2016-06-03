//Compilable with Java8
package com.puppycrawl.tools.checkstyle.grammars.java8;
import java.util.Arrays;
import java.util.List;

public class InputLambda4 {
	
	public void doSomething() {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
		
		numbers.forEach((Integer value) -> System.out.println(value));
	}
}
