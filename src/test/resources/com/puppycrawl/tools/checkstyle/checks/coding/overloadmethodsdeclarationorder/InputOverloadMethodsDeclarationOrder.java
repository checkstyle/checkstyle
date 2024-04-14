/*
OverloadMethodsDeclarationOrder


*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

class InputOverloadMethodsDeclarationOrder
{
    public void overloadMethod(int i)
    {
        //some foo code
    }

    public void overloadMethod(String s)
    {
        //some foo code
    }

    public void overloadMethod(boolean b)
    {
        //some foo code
    }

    public void fooMethod()
    {

    }

    //violation: because overloads never split
    public void overloadMethod(String s, Boolean b, int i) // violation
    {
        //some foo code
    }

    InputOverloadMethodsDeclarationOrder anonymous = new InputOverloadMethodsDeclarationOrder()
    {
        public void overloadMethod(int i)
        {
            //some foo code
        }

        public void overloadMethod(String s)
        {
            //some foo code
        }

        public void overloadMethod(boolean b)
        {
            //some foo code
        }

        public void fooMethod()
        {

        }

        //violation: because overloads never split
        public void overloadMethod(String s, Boolean b, int i) // violation
        {
            //some foo code
        };

        public void overloadMethod(double d) // violation
                                            // because there is an unnecessary semicolon at the
                                            // end of the above method,
                                            // separating the overloaded methods.
        {
            // some foo code
        }
    };
}

interface Fooable
{
    public abstract void foo(int i);
    public abstract void foo(String s);
    public abstract void noFoo();
    public abstract void foo(String s, Boolean b, int i); // violation
}

enum FooType {
    Strategy(""),
    Shooter(""),
    RPG("");

    private String description;

    private FooType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void overloadMethod(int i)
    {
        //some foo code
    }

    public void overloadMethod(String s)
    {
        //some foo code
    }

    // comments between overloaded methods are allowed.
    public void overloadMethod(boolean b)
    {
        //some foo code
    }

    public void fooMethod()
    {

    }

    //violation: because overloads never split
    public void overloadMethod(String s, Boolean b, int i) // violation
    {
        //some foo code
    }

    void test() {}

    String str;

    private interface Testing {}

    void test(int x) {} // violation

    private class Inner {
        void test() {}

        void test(String str) {}

        void test2() {}

        String str;

        void test(int x) {} // violation
    }

    void test(double d) {} // violation
}

enum Foo2 {
    VALUE {
        public void value() {
            value("");
        }

        public void middle() {
        }

        public void value(String s) {
        }
    };
}

@interface ClassPreamble {
    String author();
}
