////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2003
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

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
