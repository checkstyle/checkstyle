//Compilable with Java8
package com.puppycrawl.tools.checkstyle.grammars.java8;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

public class InputMethodReferences<T> extends ParentClass
{

    public void main(String[] args) {
		
		List<Integer> numbers = Arrays.asList(1,2,3,4,5,6);
		
		numbers.forEach(System.out::println);
		InputMethodReferences tl = new InputMethodReferences();

		String roster = new String();
		Supplier<InputMethodReferences> supplier = InputMethodReferences<String>::new;

		numbers.forEach(this::println);
		
		numbers.forEach(super::println);
        Supplier<InputMethodReferences2> supplier = InputMethodReferences2::new;
        Supplier<InputMethodReferences2> suppl = InputMethodReferences2::<Integer> new;
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
