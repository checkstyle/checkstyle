/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputRequireThisEnumInnerClassesAndBugs {
    int i;
    void method1() {
        i = 3; // violation
    }

    void method2(int i) {
        i++;
        this.i = i;
        method1(); // violation
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
        i = 3; // violation
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
            z = 1; // violation
        }
    };

    int z;
    private MyEnum()
    {
        z = 0; // violation
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
        @Rock(band = {"Tool"})
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
        i++; // violation
        i++; int i = 1; i++; // violation
        instanceMethod(); // violation
    }
    void instanceMethod() {};

    class Nested {
        void bar() {
            instanceMethod(); // violation
            i++; // violation
        }
    }
}

class Issue2539{
    void foo(int i) {}
    static void foo(double i) {}
    void foo() {}

    void bar() {
        foo(1);
        foo(); // violation
    }
}
class NestedRechange {
    final String s = "";

    NestedRechange() {
        String s = "t";
        s = s.substring(0); // violation
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
        return a + a * a; // 3 violations
    }

    public int oneReturnInMethod3() {
        for (int b = 0; b < 10; b++) {
        }
        return b + b * b; // 3 violations
    }
    final NestedFrames NestedFrames = new NestedFrames();
}
class Another {
    void method1() {
       for (int i = 0; i < 1; i++) {
           i = i + 1;
       }
       for (int i = 0; i < 1; i++) {
           for (int j = 0; j < 1; i++) {
               --i;
           }
       }
   }
   private int i;
}
class TestClass {
    private final TestClass field = new TestClass();

    private String child;

    public void method() {
        if (false) {
            return;
        } else if (true) {
            String child = (String) this.child;
            if (!(this.child instanceof String)) {
                child = field.get(child); // violation
            }
        }
    }

    public String get(String s) {
        return s;
    }
}
class TestClass3 {
    private static class Flags {
        public void method() {
            final char ch = ' ';
            parse(ch);
        }

        private static void parse(char c) {
        }
    }

    private void parse(String s) {
    }
}
