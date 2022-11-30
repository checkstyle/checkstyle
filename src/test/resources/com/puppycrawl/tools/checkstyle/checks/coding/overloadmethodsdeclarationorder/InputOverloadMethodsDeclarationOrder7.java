/*
OverloadMethodsDeclarationOrder
modifierGroups =

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

class InputOverloadMethodsDeclarationOrder7
{
    public void overloadMethod(int i) { }

    public void overloadMethod(String s) { }

    public void overloadMethod(boolean b) { }

    public void fooMethod() { }

    //violation below 'All overloaded methods should be placed next to each other'
    public void overloadMethod(String s, Boolean b, int i)
    {
        //some foo code
    }

    InputOverloadMethodsDeclarationOrder anonymous = new InputOverloadMethodsDeclarationOrder()
    {
        public void overloadMethod(int i) { }

        public void overloadMethod(String s) { }

        public void overloadMethod(boolean b) { }

        public void fooMethod() { }

        //violation below 'All overloaded methods should be placed next to each other'
        public void overloadMethod(String s, Boolean b, int i)
        {
            //some foo code
        }
    };
}

interface InputOverloadMethodsDeclarationOrder7_2
{
    public abstract void foo(int i);
    public abstract void foo(String s);
    public abstract void noFoo();
    //violation below 'All overloaded methods should be placed next to each other'
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void overloadMethod(int i) { }

    public void overloadMethod(String s) { }

    public void overloadMethod(boolean b) { }

    public void fooMethod() { }

    //violation below 'All overloaded methods should be placed next to each other'
    public void overloadMethod(String s, Boolean b, int i)
    {
        //some foo code
    }
}

enum InputOverloadMethodsDeclarationOrder7_4 {
    VALUE {
        public void value() {
            value("");
        }

        public void middle() { }

        public void value(String s) { }
    };
}

@interface InputOverloadMethodsDeclarationOrder7_5 {
    String author();
}
