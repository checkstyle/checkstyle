//Compilable with Java8
package com.puppycrawl.tools.checkstyle.grammars.java8;
public interface InputDefaultMethods {
	
	default public void doSomething(){
		System.out.println("Something done.");
	}
	
	public void doOneMoreThing();
	
}
