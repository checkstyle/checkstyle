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

class NoSuperClone // violation 'Top-level class NoSuperClone has to reside in its own source file.'
{
}

class InnerClone // violation 'Top-level class InnerClone has to reside in its own source file.'
{
}

class CloneWithTypeArguments<T> extends CloneWithTypeArgumentsAndNoSuper<T> // violation 'Top-level class CloneWithTypeArguments has to reside in its own source file.'
{
}

class CloneWithTypeArgumentsAndNoSuper<T> // violation 'Top-level class CloneWithTypeArgumentsAndNoSuper has to reside in its own source file.'
{
}

class MyClassWithGenericSuperMethod // violation 'Top-level class MyClassWithGenericSuperMethod has to reside in its own source file.'
{
}

class AnotherClass { // violation 'Top-level class AnotherClass has to reside in its own source file.'
}

class NativeTest { // violation 'Top-level class NativeTest has to reside in its own source file.'
    public native Object clone();
}
