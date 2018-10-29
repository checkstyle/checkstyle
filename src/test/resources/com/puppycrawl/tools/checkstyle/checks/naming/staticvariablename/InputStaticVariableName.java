package com.puppycrawl.tools.checkstyle.checks.naming.staticvariablename;

public class InputStaticVariableName {

	/** Interface fields should be skipped */
	interface A {
	    public static int VAL_0 = 1;
	}

	/** Annotation fields should be skipped */
	@interface B {
	    String name() default "";
	    int version() default 0;
	    public static int VAL_1 = 0;
	}
}
