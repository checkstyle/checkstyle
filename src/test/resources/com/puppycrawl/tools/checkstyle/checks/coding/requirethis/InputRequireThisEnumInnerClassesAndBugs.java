package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputRequireThisEnumInnerClassesAndBugs {
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

class Issue257 {
    public void foo() {
        try (final InputStream foo = new ByteArrayInputStream(new byte[512])) {
            foo.read();
        }
        catch (final IOException e) {
            e.getCause();
        }
    }
}

class Issue2240 {
    int i;
    void foo() {
        i++;
        i++; int i = 1; i++;
        instanceMethod();
    }
    void instanceMethod() {};

    class Nested {
        void bar() {
            instanceMethod();
            i++;
        }
    }
}

class Issue2539{
    void foo(int i) {}
    static void foo(double i) {}
    void foo() {}

    void bar() {
        foo(1);
        foo();
    }
}
class NestedRechange {
    final String s = "";

    NestedRechange() {
        String s = "t";
        s = s.substring(0);
    }

    private static class NestedStatic {
        static final String s = "";

        public void method() {
            s.substring(0);
        }
    }
}
class NestedFrames {
    int a = 0;
    int b = 0;

    public int oneReturnInMethod2() {
        for (int i = 0; i < 10; i++) {
            int a = 1;
            if (a != 2 && true) {
                if (true | false) {
                    if (a - a != 0) {
                        a += 1;
                    }
                }
            }
        }
        return a + a * a;
    }

    public int oneReturnInMethod3() {
        for (int b = 0; b < 10; b++) {
        }
        return b + b * b;
    }
}
