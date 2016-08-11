//Compilable with Java8
package com.puppycrawl.tools.checkstyle.grammars.java8;

import java.util.logging.Logger;

public class InputLambda1 {

	private static final Logger LOG = Logger.getLogger(InputLambda1.class.getName());

	static Runnable r1 = ()->LOG.info("Hello world one!");
	static Runnable r2 = () -> LOG.info("Hello world two!");
	
	public static void main(String[] args) {
		r1.run();
		r2.run();
	}
}
