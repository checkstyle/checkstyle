package com.puppycrawl.tools.checkstyle.grammars.java8;

import java.util.List;

public class InputAnnotationsTest1 {
	
	@NonNull
	List<Integer> numbers;

	@interface NonNull {

	}
	
}