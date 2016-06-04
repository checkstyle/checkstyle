//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.modifier;

public interface InputFinalInDefaultMethods {
	final int k = 5; // violation
	
    default int defaultMethod(final int x) {
    	if (k == 5) {
    		final int t = 24;  //No violation here!
    		for (; ;) {
    			final String s = "some";  //No violation here!
    		}
    	}
        final int square = x * x;  //No violation here!
        return square;
    }
}
