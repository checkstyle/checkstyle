/*
OverloadMethodsDeclarationOrder
modifierGroups =

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

class InputOverloadMethodsDeclarationOrder7
{
    public void overloadMethod(int i) { } // ok

    public void overloadMethod(String s) { } // ok

    public void overloadMethod(boolean b) { } // ok

    public void fooMethod() { } // ok

    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    public void overloadMethod(String s, Boolean b, int i)
    {
        //some foo code
    }

    InputOverloadMethodsDeclarationOrder anonymous = new InputOverloadMethodsDeclarationOrder()
    {
        public void overloadMethod(int i) { } // ok

        public void overloadMethod(String s) { } // ok

        public void overloadMethod(boolean b) { } // ok

        public void fooMethod() { } // ok

        // violation below 'All overloaded methods.*Previous overloaded method located at.*'
        public void overloadMethod(String s, Boolean b, int i)
        {
            //some foo code
        }
    };
}

interface InputOverloadMethodsDeclarationOrder7_2
{
    public abstract void foo(int i); // ok
    abstract void foo(String s); // ok
    public void foo(long l); // ok
    public abstract void noFoo(); // ok
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    void foo(String s, Boolean b, int i);
}

enum InputOverloadMethodsDeclarationOrder7_3 {
    Strategy(""),
    Shooter(""),
    RPG("");

    private String description;

    private InputOverloadMethodsDeclarationOrder7_3(String description) {
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

    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    public void overloadMethod(String s, Boolean b, int i)
    {
        //some foo code
    }
}

enum InputOverloadMethodsDeclarationOrder7_4 {
    VALUE {
        public void value() {
            value("");
        } // ok

        public void middle() { } // ok

        public void value(String s) { } // ok
    };
}

@interface InputOverloadMethodsDeclarationOrder7_5 {
    String author(); // ok
}
