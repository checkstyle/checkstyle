//Compilable with Java8
package com.puppycrawl.tools.checkstyle.grammars.java8;
import java.util.Arrays;
import java.util.List;


public class InputLambda14 {
	
	public static void main(String args[]) {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		
		numbers.forEach(first -> {
			System.out.println("first");
			System.out.println("second");
			System.out.println("third");
		});
	}
}
