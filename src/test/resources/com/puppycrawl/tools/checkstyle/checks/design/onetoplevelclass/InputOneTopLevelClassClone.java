package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;
public class InputOneTopLevelClassClone
{/* class body */
    public InputOneTopLevelClassClone() throws CloneNotSupportedException
    { //constructor body
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
}

class InnerClone
{
}

class CloneWithTypeArguments<T> extends CloneWithTypeArgumentsAndNoSuper<T>
{
}

class CloneWithTypeArgumentsAndNoSuper<T>
{
}

class MyClassWithGenericSuperMethod
{
}

class AnotherClass {
}

class NativeTest {
    public native Object clone();
}
