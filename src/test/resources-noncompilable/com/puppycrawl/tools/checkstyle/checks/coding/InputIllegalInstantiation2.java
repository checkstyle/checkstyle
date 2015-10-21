//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.coding;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

public class InputIllegalInstantiation2
{

    public static void main(String[] args)
    {

        Supplier<InputMethodReferencesTest2> supplier = InputMethodReferencesTest2::new;
        Supplier<InputMethodReferencesTest2> suppl = InputMethodReferencesTest2::<Integer> new;
        Function<Integer, Message[]> messageArrayFactory = Message[]::new;

    }

    private class Bar<T>
    {

    }
}
