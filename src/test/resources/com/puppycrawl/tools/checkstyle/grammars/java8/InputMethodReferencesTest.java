package com.puppycrawl.tools.checkstyle.grammars.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

public class InputMethodReferencesTest<T> extends ParentClass
{

    public void main(String[] args) {
		
		List<Integer> numbers = Arrays.asList(1,2,3,4,5,6);
		
		numbers.forEach(System.out::println);
		InputMethodReferencesTest tl = new InputMethodReferencesTest();

		String roster = new String();
		Supplier<InputMethodReferencesTest> supplier = InputMethodReferencesTest<String>::new;

		numbers.forEach(this::println);
		
		numbers.forEach(super::println);
        Supplier<InputMethodReferencesTest2> supplier = InputMethodReferencesTest2::new;
        Supplier<InputMethodReferencesTest2> suppl = InputMethodReferencesTest2::<Integer> new;
        Function<Integer, Message[]> messageArrayFactory = Message[]::new;
		
	}
}

class ParentClass
{

    public String println(Integer i)
    {
        return null;
    }
}
