package com.puppycrawl.tools.checkstyle.grammars.java8;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;

public class InputAnnotationsTest7 {
	
	public static void main(String[] args) {
		Object object = new @Interned Object();
		
	}
	
	@Target(ElementType.TYPE_USE)
	@interface Interned {

	}	
	
}