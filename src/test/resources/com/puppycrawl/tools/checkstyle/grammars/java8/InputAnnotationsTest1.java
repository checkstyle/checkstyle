package com.puppycrawl.tools.checkstyle.grammars.java8;

import java.util.Arrays;
import java.util.List;

public class InputTypeAnnotationsTest1 {
	
	@NonNull
	List<Integer> numbers;

	public static void main(String[] args) {
		Object str = "";
		String myString = (@NonNull String) str;
		Object object = new @Interned Object();
		
	}
	
	void monitorTemperature() throws @Critical Exception {  }

	abstract class UnmodifiableList<T> implements @Readonly List<@Readonly T> {
	}

	@interface NonNull {

	}
	
	@interface Readonly {

	}	
	
	@interface Interned {

	}	
	
	@interface Critical {

	}
}
