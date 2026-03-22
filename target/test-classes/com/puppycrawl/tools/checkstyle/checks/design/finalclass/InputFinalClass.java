/*
FinalClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

import java.util.ArrayList;

public class InputFinalClass // violation 'Class InputFinalClass should be declared as final'
{
    private InputFinalClass() {}
}

final class test2 {}
class test3
{
   class test4 // violation 'Class test4 should be declared as final'
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

// abstract class without its own abstract method decl
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

