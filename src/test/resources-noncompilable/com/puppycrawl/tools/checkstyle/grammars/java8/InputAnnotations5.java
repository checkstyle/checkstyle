//Compilable with Java8
package com.puppycrawl.tools.checkstyle.grammars.java8;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


public class InputAnnotations5 {
	
	
	void monitorTemperature() throws @Critical Exception {  }
	void monitorTemperature2() throws NullPointerException, @Critical Exception {  }

	@Target(ElementType.TYPE_USE)
	@interface Critical {

	}
}
