package com.puppycrawl.tools.checkstyle.grammars.java8;

import java.util.Arrays;
import java.util.List;

@Schedule
public class InputTypeAnnotationsTest2 {
	
	@Repeatable(Schedules.class)
	public @interface Schedule { 
		Schedule[] value;
	}
}
