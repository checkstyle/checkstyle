package com.puppycrawl.tools.checkstyle.coding;
public class InputClone
{
    public InputClone() throws CloneNotSupportedException
    {
        super.equals(new String());
        super.clone();
    }
    
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
    
    public void method() throws CloneNotSupportedException
    {
        super.clone();
    }
    
    {
        super.clone();
    }
}

class NoSuperClone
{
    public Object clone()
    {
        return null;
    }
}

class InnerClone
{
    public Object clone()
    {
        class Inner
        {
            public Object clone() throws CloneNotSupportedException
            {
                return super.clone();
            }
        }
        return null;
    }
}

// This could not pass as valid semantically but tests that
// type arguments are ignored when checking super calls
class CloneWithTypeArguments
{
    public <T> Object clone()
    {
        return super.<T>clone();
    }
}

class CloneWithTypeArgumentsAndNoSuper
{
    public <T> Object clone()
    {
        return null;
    }
}

//Check that super keword isn't snagged here
class MyClassWithGenericSuperMethod
{
    void someMethod(java.util.List<? super java.util.Map> l)
    {

    }
}
