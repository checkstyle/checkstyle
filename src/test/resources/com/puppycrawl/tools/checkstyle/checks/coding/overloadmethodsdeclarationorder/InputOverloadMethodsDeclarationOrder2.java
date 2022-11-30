/*
OverloadMethodsDeclarationOrder
modifierGroups = .*

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

class InputOverloadMethodsDeclarationOrder2
{
    public void overloadMethod(int i) { } // ok

    public void overloadMethod(String s) { } // ok

    public void overloadMethod(boolean b) { } // ok

    public void fooMethod() { } // ok

    //violation below 'All overloaded methods should be placed'
    public void overloadMethod(String s, Boolean b, int i) {

    }

    InputOverloadMethodsDeclarationOrder anonymous = new InputOverloadMethodsDeclarationOrder()
    {
        public void overloadMethod(int i) { } // ok

        public void overloadMethod(String s) { } // ok

        public void overloadMethod(boolean b) { } // ok

        public void fooMethod() { } // ok

        //violation below 'All overloaded methods should be placed'
        public void overloadMethod(String s, Boolean b, int i)
        {
            //some foo code
        }
    };
}

interface FooableBackwardsCompatible
{
    public abstract void foo(int i); // ok
    public abstract void foo(String s); // ok
    public abstract void noFoo(); // ok
    public abstract void foo(String s, Boolean b, int i);
    //violation above 'All overloaded methods should be placed'
}

enum FooTypeBackwardsCompatible {
    Strategy(""),
    Shooter(""),
    RPG("");

    private String description;

    private FooTypeBackwardsCompatible(String description) {
        this.description = description;
    }

    public String getDescription() { // ok
        return description;
    }

    public void setDescription(String description) { // ok
        this.description = description;
    }

    public void overloadMethod(int i) { } // ok

    public void overloadMethod(String s) { } // ok

    public void overloadMethod(boolean b) { } // ok

    public void fooMethod() { } // ok

    //violation below 'All overloaded methods should be placed'
    public void overloadMethod(String s, Boolean b, int i)
    {
        //some foo code
    }
}

enum Foo2BackwardsCompatible {
    VALUE {
        public void value() {
            value("");
        } // ok

        public void middle() { } // ok

        public void value(String s) { } // ok
    };
}

@interface ClassPreambleBackwardsCompatible {
    String author(); // ok
}
