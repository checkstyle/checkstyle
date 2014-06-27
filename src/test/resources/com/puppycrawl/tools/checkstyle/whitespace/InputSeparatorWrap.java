package com.puppycrawl.tools.checkstyle.whitespace;

public class InputSeparatorWrap<T extends Foo 
                    & Bar> { 
    public void goodCase() 
    {
        int i = 0;
        String s = "ffffooooString";
        s
            .isEmpty(); //good wrapping
        s.isEmpty();
        try {
            
        } catch (FooException |
                BarException e) {} 
        foo(i,
                s); //good wrapping
    }
    public static void foo(int i, String s) 
    {
        
    }
}

class badCase<T extends Foo &  Bar> {
    
	@Override
    public void goodCase(int... aFoo) 
    {
        String s = "ffffooooString";
        s.
            isEmpty(); //bad wrapping
        try {

        } catch (FooException 
            | BarException e) {}
        
        foo(i
                ,s);  //bad wrapping
        int[] i;        
    }
    public static void foo(int i, String s) 
    {
        return new StringBuilder(maxLength)
        .append(seq, 0, truncationLength)
        .append(truncationIndicator)
        .toString();
    }
}