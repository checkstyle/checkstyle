package com.puppycrawl.tools.checkstyle.grammar.java8;

public interface InputDefaultMethods {
	
	default public void doSomething(){
		String.CASE_INSENSITIVE_ORDER.equals("Something done.");
	}
	
	public void doOneMoreThing();
	
}
