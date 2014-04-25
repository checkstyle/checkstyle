/*
 * InputValidMethodIndent.java
 *
 * Created on November 11, 2002, 10:13 PM
 */

package com.puppycrawl.tools.checkstyle.indentation;

import java.util.Arrays;

/**
 *
 * @author  jrichard
 */
public class InputValidMethodIndent extends java.awt.event.MouseAdapter implements java.awt.event.MouseListener {
    
    // ctor with rcurly on same line
    public InputValidMethodIndent() {
    }

    private InputValidMethodIndent(boolean test) {
        boolean test2 = true;
        
        int i = 4 + 
            4;
    }

    
    private InputValidMethodIndent(boolean test,
        boolean test2) {
        boolean test3 = true;
        
        int i = 4 + 
            4;
    }

    
    private InputValidMethodIndent(boolean test,
        boolean test2, boolean test3) 
    {
        boolean test4 = true;
        
        int i = 4 + 
            4;
    }
    
    // ctor with rcurly on next line
    public InputValidMethodIndent(int dummy)
    {
    }

    // method with rcurly on same line
    public String method1() {
        return "hi";
    }

    // method with rcurly on next line
    public void method2()
    {
    }
    
    // method with a bunch of params
    public int method2(int x, int y, int w, int h)
    {
        return 1;
    }
    
    // params on multiple lines
    public void method2(int x, int y, int w, int h,
        int x1, int y1, int w1, int h1)
    {
    }

    // params on multiple lines
    public void method3(int x, int y, int w, int h,
        int x1, int y1, int w1, int h1)
    {
        System.getProperty("foo");
    }

    // params on multiple lines
    public void method4(int x, int y, int w, int h,
        int x1, int y1, int w1, int h1)
    {
        boolean test = true;
        
        int i = 4 + 
            4;
        
        i += 5;
        i += 5 
            + 4;
        if (test) 
        {
            System.getProperty("foo");
        } else {
            System.getProperty("foo");
        }
        
        for (int j=0;j<10; j++) {
            System.getProperty("foo");
        }

        
        myfunc2(10, 10, 10,
            myfunc3(11, 11,
                11, 11),
            10, 10,
            10);
        

        myfunc3(11, 11, Integer.
                getInteger("mytest").intValue(),
            11);
        
        myfunc3(
            1,
            2, 
                3, 
                4);
    }

    // strange IMHO, but I suppose this should be allowed
    public
    void
    method5() {
    }
    
          
    private void myfunc2(int a, int b, int c, int d, int e, int f, int g) {
    }
    
    private int myfunc3(int a, int b, int c, int d) {
        return 1;
    }
    
    
    void method6() {
        System.out.println("methods are: " + Arrays.asList(
            new String[] {"method"}).toString());

        System.out.println("methods are: " + Arrays.asList(
            new String[] {"method"}
        ).toString());

        System.out.println("methods are: " + Arrays.asList(
            new String[] {"method"}).toString()
        );

            
        myfunc2(3, 4, 5, 
            6, 7, 8, 9);
            
        myfunc2(3, 4, method2(3, 4, 5, 6) + 5, 
            6, 7, 8, 9);
            
        System.out.println("methods are: " + 
            Arrays.asList(
                new String[] {"method"}).toString());
        
        System.out.println("methods are: " 
            + Arrays.asList(
                new String[] {"method"}).toString());
                

        String blah = (String) System.getProperty(
            new String("type"));
        
        System.out.println(method1() + "mytext" 
            + " at indentation level not at correct indentation, " 
            + method1());

        System.out.println(
            method1() + "mytext" 
                + " at indentation level not at correct indentation, " 
                + method1());

        System.out.toString()
            .equals("blah");


   
    }

    private int[] getArray() {
        return new int[] {1};
    }

    private void indexTest() {
        getArray()[0] = 2;
    }
        
}
