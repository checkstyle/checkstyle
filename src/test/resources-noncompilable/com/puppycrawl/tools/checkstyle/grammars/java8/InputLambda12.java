//Compilable with Java8
package com.puppycrawl.tools.checkstyle.grammars.java8;
public class InputLambda12 {

	public static void testVoidLambda(TestOfVoidLambdas test) {
		System.out.println("Method called");
		test.doSmth("fef", 5);
	}
	
	
	public static void main(String[] args) {
		
		testVoidLambda((String s1, Integer i2) -> System.out.println(s1));
	}

	private interface TestOfVoidLambdas {

		public void doSmth(String first, Integer second);
	}
}
