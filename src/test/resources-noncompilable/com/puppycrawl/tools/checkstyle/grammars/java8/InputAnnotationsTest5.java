package com.puppycrawl.tools.checkstyle.grammars.java8;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


public class InputAnnotationsTest5 {
	
	
	void monitorTemperature() throws @Critical Exception {  }

	@Target(ElementType.TYPE_USE)
	@interface Critical {

	}
}