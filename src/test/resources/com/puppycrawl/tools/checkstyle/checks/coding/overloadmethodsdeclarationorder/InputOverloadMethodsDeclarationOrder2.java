/*
OverloadMethodsDeclarationOrder
modifierGroups = .*

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

class InputOverloadMethodsDeclarationOrder2
{
    public void overloadMethod(int i) { }

    public void overloadMethod(String s) { }

    public void overloadMethod(boolean b) { }

    public void fooMethod() { }

    //violation: because overloads never split
    public void overloadMethod(String s, Boolean b, int i) // violation
    {
        //some foo code
    }

    InputOverloadMethodsDeclarationOrder anonymous = new InputOverloadMethodsDeclarationOrder()
    {
        public void overloadMethod(int i) { }

        public void overloadMethod(String s) { }

        public void overloadMethod(boolean b) { }

        public void fooMethod() { }

        //violation: because overloads never split
        public void overloadMethod(String s, Boolean b, int i) // violation
        {
            //some foo code
        }
    };
}

interface FooableBackwardsCompatible
{
    public abstract void foo(int i);
    public abstract void foo(String s);
    public abstract void noFoo();
    public abstract void foo(String s, Boolean b, int i); // violation
}

enum FooTypeBackwardsCompatible {
    Strategy(""),
    Shooter(""),
    RPG("");

    private String description;

    private FooTypeBackwardsCompatible(String description) {
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

    //violation: because overloads never split
    public void overloadMethod(String s, Boolean b, int i) // violation
    {
        //some foo code
    }
}

enum Foo2BackwardsCompatible {
    VALUE {
        public void value() {
            value("");
        }

        public void middle() { }

        public void value(String s) { }
    };
}

@interface ClassPreambleBackwardsCompatible {
    String author();
}
