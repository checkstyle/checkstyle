package com.puppycrawl.tools.checkstyle.grammar.java8;

import java.util.logging.Logger;


public class InputLambda9 {

	private static final Logger LOG = Logger.getLogger(InputLambda9.class.getName());

	public static void testVoidLambda(TestOfVoidLambdas test) {
		LOG.info("Method called");
		test.doSmth("fef", 2);
	}
	
	
	public static void main(String[] args) {
		
		testVoidLambda((s1, s2) -> {LOG.info(s1 + s2);});
	}

	private interface TestOfVoidLambdas {

		public void doSmth(String first, Integer second);
	}
}
