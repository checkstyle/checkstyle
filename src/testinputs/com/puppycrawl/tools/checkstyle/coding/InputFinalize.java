package com.puppycrawl.tools.checkstyle.coding;
public class InputFinalize
{
    public InputFinalize() throws Throwable
    {
        super.equals(new String());
        super.finalize();
    }
    
    public void finalize() throws Throwable
    {
        super.finalize();
    }
    
    public void method() throws Throwable
    {
        super.finalize();
    }
    
    {
        super.finalize();
    }
}

class NoSuperFinalize
{
    public void finalize()
    {
    }
}

class InnerFinalize
{
    public void finalize()
    {
        class Inner
        {
            public void finalize() throws Throwable
            {
                super.finalize();
            }
        }
    }
}

enum MyEnum
{
    ; //No constants, just for the fun of it
    public void finalize() throws Throwable
    {
        super.finalize();
    }
}

enum MyEnum2
{
    ; //No constants, just for the fun of it
    public void finalize() throws Throwable
    {
        //No super.finalize()
    }
}