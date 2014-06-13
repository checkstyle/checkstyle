package com.puppycrawl.tools.checkstyle.grammars.java8;

import java.util.Arrays;
import java.util.List;

import TestLabmda.TestOfVoidLambdas;

public class InputLabdaTest3 {
	
	public static void testVoidLambda(TestOfVoidLambdas test) {
		System.out.println("Method called");
		test.doSmth();
	}
	
	
	public static void main(String[] args) {
		testVoidLambda(() -> {
			System.out.println("Method in interface called");
		});
	}

	private interface TestOfVoidLambdas {

		public void doSmth();
	}
}
