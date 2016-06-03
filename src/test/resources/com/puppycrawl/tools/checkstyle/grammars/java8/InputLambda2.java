//Compilable with Java8
package com.puppycrawl.tools.checkstyle.grammars.java8;
public class InputLambda2 {

	public static void testVoidLambda(TestOfVoidLambdas test) {
		System.out.println("Method called");
		test.doSmth();
	}
	
	
	public static void main(String[] args) {
		testVoidLambda(() -> System.out.println("Method in interface called"));
	}

	private interface TestOfVoidLambdas {

		public void doSmth();
	}
}
