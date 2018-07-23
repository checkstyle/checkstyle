package com.puppycrawl.tools.checkstyle.grammar.java8;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;



public class InputAnnotations4 {
	
	public static void methodName(@NotNull String args) {
		
	}
	
	@Target(ElementType.TYPE_USE)
	@interface NotNull {

	}
}
