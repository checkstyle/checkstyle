// Config: FinalClass
package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public class InputFinalClassCompact // violation 'Class InputFinalClassCompact should be declared as final'
{
    private InputFinalClassCompact() {}
}

final class test2 {}

class test3 {
    class test4 // violation 'Class test4 should be declared as final'
    {
        private test4() {}
    }
}

class test5 {
    private test5() {}
    test5(int i) {}
}

class test6 {
    public test6() {}
}

final class test7 {
    private test7() {}
}

// Typesafe enum with operation
abstract class Operation {
    abstract double eval(double a, double b);

    public static final Operation PLUS = new Operation("+") {
        double eval(double a, double b) {
            return a + b;
        }
    };

    public static final Operation MINUS = new Operation("-") {
        double eval(double a, double b) {
            return a - b;
        }
    };

    private String _name;
    private Operation(String name) {
        this._name = name;
    }
}

interface Evaluable {
    double eval(double a, double b);
}

abstract class Operation2 implements Evaluable {
    public static final Operation2 PLUS = new Operation2("+") {
        public double eval(double a, double b) {
            return a + b;
        }
    };

    public static final Operation2 MINUS = new Operation2("-") {
        public double eval(double a, double b) {
            return a - b;
        }
    };

    private String _name;
    private Operation2(String name) {
        this._name = name;
    }
}
