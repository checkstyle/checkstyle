/*
SuperFinalize


*/

package com.puppycrawl.tools.checkstyle.checks.coding.superfinalize;

public class InputSuperFinalizeVariations
{
    public InputSuperFinalizeVariations() throws Throwable
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
    public void finalize() // violation "Overriding finalize() method must invoke super.finalize() to ensure proper finalization."
    {
    }
}

class InputInnerFinalize
{
    public void finalize() // violation "Overriding finalize() method must invoke super.finalize() to ensure proper finalization."
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

//Check that super keyword isn't snagged here
class MyClassWithGenericSuperMethod1
{
    void someMethod(java.util.List<? super java.util.Map> l)
    {

    }
}

class TestNative {
    public native void finalize();
}

class InputOneMore {

    public void doSmt() throws Throwable {
        {
            {
                super.finalize();
            }
        }
    }
}

class FinalizeWithArgs {
    public void finalize(Object a) {};
}

class OverrideClass extends FinalizeWithArgs {
    @Override
    protected void finalize() throws Throwable { // violation "Overriding finalize() method must invoke super.finalize() to ensure proper finalization."
        super.finalize(new Object());
    }
}
