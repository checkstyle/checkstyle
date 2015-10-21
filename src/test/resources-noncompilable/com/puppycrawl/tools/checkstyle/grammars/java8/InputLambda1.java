//Compilable with Java8
package com.puppycrawl.tools.checkstyle.grammars.java8;
public class InputLambda1 {
	
	static Runnable r1 = ()->System.out.println("Hello world one!");
	static Runnable r2 = () -> System.out.println("Hello world two!");
	
	public static void main(String[] args) {
		r1.run();
		r2.run();
	}
}
