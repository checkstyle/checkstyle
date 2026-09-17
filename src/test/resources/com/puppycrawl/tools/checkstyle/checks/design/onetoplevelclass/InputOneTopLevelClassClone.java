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

// violation below 'Top-level class NoSuperClone has to reside in its own source file.'
class NoSuperClone
{
}

// violation below 'Top-level class InnerClone has to reside in its own source file.'
class InnerClone
{
}

// violation below 'Top-level class CloneWithTypeArguments has to reside in its own source file.'
class CloneWithTypeArguments<T> extends CloneWithTypeArgumentsAndNoSuper<T>
{
}

// violation below 'Top-level class Clone.* has to reside in its own source file.'
class CloneWithTypeArgumentsAndNoSuper<T>
{
}

// violation below 'Top-level class My.* has to reside in its own source file.'
class MyClassWithGenericSuperMethod
{
}

// violation below 'Top-level class AnotherClass has to reside in its own source file.'
class AnotherClass {
}

// violation below 'Top-level class NativeTest has to reside in its own source file.'
class NativeTest {
    public native Object clone();
}
