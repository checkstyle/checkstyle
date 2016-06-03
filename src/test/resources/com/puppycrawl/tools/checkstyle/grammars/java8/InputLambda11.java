//Compilable with Java8
package com.puppycrawl.tools.checkstyle.grammars.java8;
public class InputLambda11 {

	public static void testVoidLambda(TestOfVoidLambdas test) {
		System.out.println("Method called");
		test.doSmth("fef");
	}
	
	
	public static void main(String[] args) {
		
		testVoidLambda(s1 -> {System.out.println(s1);});
	}

	private interface TestOfVoidLambdas {

		public void doSmth(String first);
	}
}
