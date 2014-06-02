package com.puppycrawl.tools.checkstyle.grammars.java8;

import java.util.Arrays;
import java.util.List;

public class InputMethodReferencesTest {
	
	public static void main(String[] args) {
		
		List<Integer> numbers = Arrays.asList(1,2,3,4,5,6);
		
		numbers.forEach(System.out::println);
		InputMethodReferences<String> tl = new InputMethodReferences<String>();

		Set<Person> rosterSet = transferElements(roster, HashSet<Person>::new);

		numbers.forEach(this::println);
		
		numbers.forEach(super::println);
		
	}

}
