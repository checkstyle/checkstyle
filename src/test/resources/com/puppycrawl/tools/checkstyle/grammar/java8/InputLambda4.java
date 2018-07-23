package com.puppycrawl.tools.checkstyle.grammar.java8;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


public class InputLambda4 {

	private static final Logger LOG = Logger.getLogger(InputLambda4.class.getName());

	public void doSomething() {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
		
		numbers.forEach((Integer value) -> LOG.info(value.toString()));
	}
}
