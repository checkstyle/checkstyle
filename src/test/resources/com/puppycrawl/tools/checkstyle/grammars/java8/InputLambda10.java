//Compilable with Java8
package com.puppycrawl.tools.checkstyle.grammars.java8;

import java.util.logging.Logger;

public class InputLambda10 {

	private static final Logger LOG = Logger.getLogger(InputLambda10.class.getName());

	public static void testVoidLambda(TestOfVoidLambdas test) {
		LOG.info("Method called");
		test.doSmth("fef");
	}
	
	
	public static void main(String[] args) {
		
		testVoidLambda(s1 -> LOG.info(s1));
	}

	private interface TestOfVoidLambdas {

		public void doSmth(String first);
	}
}
