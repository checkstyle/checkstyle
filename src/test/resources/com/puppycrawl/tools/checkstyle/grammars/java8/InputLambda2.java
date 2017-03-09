package com.puppycrawl.tools.checkstyle.grammars.java8;

import java.util.logging.Logger;


public class InputLambda2 {

	private static final Logger LOG = Logger.getLogger(InputLambda2.class.getName());

	public static void testVoidLambda(TestOfVoidLambdas test) {
		LOG.info("Method called");
		test.doSmth();
	}
	
	
	public static void main(String[] args) {
		testVoidLambda(() -> LOG.info("Method in interface called"));
	}

	private interface TestOfVoidLambdas {

		public void doSmth();
	}
}
