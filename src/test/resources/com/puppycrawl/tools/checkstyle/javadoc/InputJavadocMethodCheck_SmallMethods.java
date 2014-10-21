package com.puppycrawl.tools.checkstyle.javadoc;

/**
 * The following is a bad tag.
 * @mytag Hello
 */
public class InputJavadocSelfExplanatoryMethodCheck
{
    //with comments

    /**
     * Some javadoc.
     * @return Some javadoc.
     */
    int foo1() 
    {
        return 1;
    }

    /**
     * Some javadoc.
     * @return Some javadoc.
     */
    String foo2() 
    {
    	return "Fooooooooooooooo"
                + "ooooo"
                + "ooo";
    }

    /**
     * Some javadoc.
     */
    void foo3() 
    {
        foo2();
    }

    /**
     * Some javadoc.
     */
    void foo4() {}

    //without comments

    int foo5() 
    {
        return 1;
    }

    String foo6() 
    {
        return "Fooooooooooooooo"
                + "oooooooo";
    }

    String foo7() 
    {
        return "Fooooooooooooooo"
                + "ooooo"
                + "ooo";
    }

    void foo8() 
    {
        foo2();
    }

    int foo8() {
        
        
        
        
        
    }

    @MyAnnotation
    String foo9() 
    {
        return "Fooooooooooooooo"
                + "ooooo"
                + "ooo";
    }

    @Override
    String foo9() 
    {
        return "Fooooo"
        		+ "ooo"
        		+ "ooooooo"
                + "ooooo"
                + "ooo";
    }
}

@interface MyAnnotation {}