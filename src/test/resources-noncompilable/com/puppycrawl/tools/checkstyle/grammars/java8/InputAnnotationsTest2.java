package com.puppycrawl.tools.checkstyle.grammars.java8;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Schedule
public class InputAnnotationsTest2 {
	
	
}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface Schedule { 

}