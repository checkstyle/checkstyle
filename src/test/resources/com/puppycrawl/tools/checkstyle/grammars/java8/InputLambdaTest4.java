package com.puppycrawl.tools.checkstyle.grammars.java8;

public class InputLabdaTest4 {
	
	public void doSomething() {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
		
		numbers.forEach((Integer value) -> System.out.println(value));
	}
}
