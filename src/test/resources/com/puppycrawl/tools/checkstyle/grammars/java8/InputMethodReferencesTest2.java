package com.puppycrawl.tools.checkstyle.grammars.java8;

import java.util.Arrays;
import java.util.List;

public class InputMethodReferencesTest2{
	
	public static void main(String[] args) {
		String::length;             // instance method
		System::currentTimeMillis;  // static method
		List<String>::size;  // explicit type arguments for generic type
		List::size;          // inferred type arguments for generic type
		int[]::clone;
		T::tvarMember;

		System.out::println;
		"abc"::length;
		foo[x]::bar;
		(test ? list.replaceAll(String::trim) : list) :: iterator;
		super::toString;
		
		String::valueOf;     // overload resolution needed     
		Arrays::sort;         // type arguments inferred from context
		Arrays::<String>sort;  // explicit type arguments
		
		ArrayList<String>::new;     // constructor for parameterized type
		ArrayList::new;             // inferred type arguments
		                           // for generic class
		Foo::<Integer>new;          // explicit type arguments
		                           // for generic constructor
		Bar<String>::<Integer>new;  // generic class, generic constructor
		Outer.Inner::new;           // inner class constructor
		int[]::new;                 // array creation
		
	}

}
