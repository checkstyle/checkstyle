/*
RequireThis
checkFields = false
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputRequireThisEnumInnerClassesAndBugs2 {
    int i;
    void method1() {
        i = 3;
    }

    void method2(int i) {
        i++;
        this.i = i;
        method1(); // violation 'Method call to 'method1' needs "this.".'
        try {
            this.method1();
        }
        catch (RuntimeException e) {
            e.toString();
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
enum MyEnum2
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
    private MyEnum2()
    {
        z = 0;
    }
}

class Bug21230032 {
    @Rock(band = "GnR")
    private String band;

    class Inner {
        @Rock(band = {"GnR"})
        private String band;
    }

    class Inner2 {
        @Rock(band = {"Tool"})
        private String band;
    }
    /*     \m/(>.<)\m/     */
    @interface Rock {
        String[] band() default "Metallica";
    }
}

class Bug11559212 {
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

interface Issue1552 {
    String BASE = "A";
    String EXT = BASE + "B";
}

class Issue2572 {
    public void foo() {
        try (final InputStream foo = new ByteArrayInputStream(new byte[512])) {
            foo.read();
        }
        catch (final IOException e) {
            e.getCause();
        }
    }
}
