/*
OneTopLevelClass


*/

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

class NoSuperClone // violation
{
}

class InnerClone // violation
{
}

class CloneWithTypeArguments<T> extends CloneWithTypeArgumentsAndNoSuper<T> // violation
{
}

class CloneWithTypeArgumentsAndNoSuper<T> // violation
{
}

class MyClassWithGenericSuperMethod // violation
{
}

class AnotherClass { // violation
}

class NativeTest { // violation
    public native Object clone();
}
