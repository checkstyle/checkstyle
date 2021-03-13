////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2003
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

import java.util.ArrayList;

public class InputFinalClass
{
    private InputFinalClass() {}
}

final class test2 {}
class test3
{
   class test4
   {
       private test4() {}
   }
}

class test5
{
    private test5() {}
    test5(int i) {}
}

class test6
{
    public test6() {}
}

final class test7 {
    private test7() {}
}

// Typesafe enum with operation
// abstract classes cannot be final, see bug #837012
abstract class Operation
{
    abstract double eval(double a, double b);

    public static final Operation PLUS =
new Operation("+")
{
    double eval(double a, double b)
    {
return a + b;
    }
};

    public static final Operation MINUS =
new Operation("-")
{
    double eval(double a, double b)
    {
return a - b;
    }
};

    private String _name;
    private Operation(String name)
    {
this._name = name;
    }
}

// Typesafe enum with operation
// abstract classes cannot be final, see bug #837012
interface Evaluable
{
    double eval(double a, double b);
}

// abstract class without it's own abstract method decl
abstract class Operation2 implements Evaluable
{

    public static final Operation2 PLUS =
new Operation2("+")
{
    public double eval(double a, double b)
    {
return a + b;
    }
};

    public static final Operation2 MINUS =
new Operation2("-")
{
    public double eval(double a, double b)
    {
return a - b;
    }
};

    private String _name;
    private Operation2(String name)
    {
this._name = name;
    }
}

enum testenum1
{
    A, B;
    testenum1() {}
}

enum testenum2
{
    A, B;

    public static class someinnerClass
    {
        private someinnerClass() {}
    }
}

interface TestInterface {
    class SomeClass {
        private SomeClass() {}
    }
}

@interface SomeAnnotation {
    class SomeClass {
        private SomeClass() {}
    }
}

class test8 {
    public static final test8 ONE = new test8() {
        @Override
        public int value() {
            return 1;
        }
    };

    private test8() {
    }

    public int value() {
        return 0;
    }
}

class test {

    private test(String s) {
        String a = "hello" + s;
    }

    public int count() {
        return 1;
    }
    public static final test ONE = new test("world");
    public static final test6 TWO = new test6();

    public static void main(String[] args) {

        ArrayList<String> foo = new ArrayList<>();
        foo.add("world");
        foo.forEach(test::new);

    }
}

interface Test1 {
    ArrayList<String> foo = new ArrayList<>();
}

