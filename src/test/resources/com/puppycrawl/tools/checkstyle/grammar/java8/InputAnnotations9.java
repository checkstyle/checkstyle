package com.puppycrawl.tools.checkstyle.grammar.java8;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;


public class InputAnnotations9 {
	public static <T> void methodName(Object str) {
		List<@Immutable ? extends Comparable<T>> unchangeable;
	}

	@Target(ElementType.TYPE_USE)
	@interface Immutable {
	}
}
