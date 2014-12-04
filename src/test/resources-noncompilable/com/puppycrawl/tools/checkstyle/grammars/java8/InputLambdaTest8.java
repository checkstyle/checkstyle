package com.puppycrawl.tools.checkstyle.grammars.java8;

public class InputLambdaTest8 {
	public static void testVoidLambda(TestOfVoidLambdas test) {
		System.out.println("Method called");
		test.doSmth("fef", 2);
	}
	
	
	public static void main(String[] args) {
		
		testVoidLambda((s1, s2) -> System.out.println(s1 + s2));
	}

	private interface TestOfVoidLambdas {

		public void doSmth(String first, Integer second);
	}
}
