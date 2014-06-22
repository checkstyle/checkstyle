package com.puppycrawl.tools.checkstyle.coding;

import java.awt.Toolkit;

public class InputRequireThis {
    int i;
    void method1() {
        i = 3;
    }

    void method2(int i) {
        i++;
        this.i = i;
        method1();
        try {
            this.method1();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        this.i--;

        Integer.toString(10);
    }

    <T> void method3()
    {
        i = 3;
    }

    void method4() {
        this.<String>method3();
        this.<I>method3();
    }
    int I = 0;
    private class I {}
}
//  enum
enum MyEnum
{
    A,
    B
    {
        void doSomething()
        {
            z = 1;
        }
    };

    int z;
    private MyEnum()
    {
        z = 0;
    }
}

class Bug2123003 {
    @Rock(band = "GnR")
    private String band;
    
    class Inner {
        @Rock(band = {"GnR"})
        private String band;
    }
    
    class Inner2 {
        @Rock(band = {(true) ? "GnR" : "Tool"})
        private String band;
    }
    /*     \m/(>.<)\m/     */
    @interface Rock {
        String[] band() default "Metallica";
    }
}

class Bug1155921 {
    private static int CONST = 1;
    private static int static_method() {
        return 1;
    }

    private int method1() {
        return CONST;
    }

    private int method2() {
        return static_method();
    }
}

interface Issue155 {
    String BASE = "A";
    String EXT = BASE + "B";
}
