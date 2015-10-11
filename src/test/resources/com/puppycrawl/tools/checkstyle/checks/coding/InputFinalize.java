package com.puppycrawl.tools.checkstyle.checks.coding;
public class InputFinalize
{
    public InputFinalize() throws Throwable
    {
        super.equals(new String());
        super.finalize();
    }
    
    public void finalize() /**comment test*/throws Throwable
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

//Check that super keword isn't snagged here
class MyClassWithGenericSuperMethod1
{
    void someMethod(java.util.List<? super java.util.Map> l)
    {

    }
}

class TestNative {
    public native void finalize();
}

class OneMore {
    
    public void doSmt() throws Throwable {
        {
            {
                super.finalize();
            }
        }
    }
}
