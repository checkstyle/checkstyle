package com.google.checkstyle.test.chapter3filestructure.rule3421overloadsplit;

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

    //error because overloads never split
    public void overloadMethod(String s, Boolean b, int i) //warn
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

        //error because overloads never split
        public void overloadMethod(String s, Boolean b, int i) //warn
        {
            //some foo code
        }
    };
}

interface Fooable
{
    public abstract void foo(int i);
    public abstract void foo(String s);
    public abstract void noFoo();
    public abstract void foo(String s, Boolean b, int i); //warn
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

    public void overloadMethod(boolean b)
    {
        //some foo code
    }

    public void fooMethod()
    {

    }

    //error because overloads never split
    public void overloadMethod(String s, Boolean b, int i) //warn
    {
        //some foo code
    }
}

