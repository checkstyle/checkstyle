package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

public class InputOperatorWrapSeparatorAndInterfaces<T extends Foo
                    & Bar> { 
    public void goodCase() throws FooException, BarException 
    {
        int i = 0;
        String s = "ffffooooString";
        s
            .isEmpty(); //good wrapping
        s.isEmpty();
        try {
            foo(i, s);
        } catch (FooException |
                BarException e) {} 
        foo(i,
                s); //good wrapping
    }
    public static void foo(int i, String s) throws FooException, BarException 
    {
        
    }
}

class badCase<T extends Foo &  Bar> {
    
	
    public void goodCase(int... aFoo) throws FooException, BarException 
    {
        String s = "ffffooooString";
        s.
            isEmpty(); //bad wrapping
        try {
            foo(1, s); 
        } catch (FooException 
            | BarException e) {}
        
        foo(1
                ,s);  //bad wrapping
        int[] i;        
    }
    public static String foo(int i, String s) throws FooException, BarException 
    {
        return new StringBuilder("")
        .append("", 0, 1)
        .append("")
        .toString();
    }
}

interface Foo {

}

interface Bar {

}

class FooException extends Exception {
	
}

class BarException extends Exception {
	
}
