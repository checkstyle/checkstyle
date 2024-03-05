package org.checkstyle.suppressionxpathfilter.superfinalize;


public class SuppressionXpathRegressionSuperFinalizeNoSuperFinalize
{
    public void finalize() // warn
    {
    }
}

class InputSuperFinalizeVariations
{
    public InputSuperFinalizeVariations() throws Throwable
    {
        super.equals(new String());
        super.finalize();
    }

    public void finalize() /* comment test */ throws Throwable
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


