/*
NoClone


*/

package com.puppycrawl.tools.checkstyle.checks.coding.noclone;

public class InputNoClone
{/* class body */
    public InputNoClone() throws CloneNotSupportedException
    { //constructor body
        super.equals(new String());
        super.clone();
    }

    public Object clone() throws CloneNotSupportedException // violation
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
    public Object clone() // violation
    {
        return null;
    }
}

class InnerClone
{
    public Object clone() // violation
    {
        class Inner
        {
            public Object clone() throws CloneNotSupportedException // violation
            {
                return super.clone();
            }
        }
        return null;
    }
}

// This could not pass as valid semantically but tests that
// type arguments are ignored when checking super calls
class CloneWithTypeArguments<T> extends CloneWithTypeArgumentsAndNoSuper<T>
{
    public CloneWithTypeArguments<T> clone() throws CloneNotSupportedException // violation
    {
        return (CloneWithTypeArguments<T>) super.<T>clone();
    }
}

class CloneWithTypeArgumentsAndNoSuper<T>
{
    public CloneWithTypeArgumentsAndNoSuper<T> clone() // violation
            throws CloneNotSupportedException
    {
        return null;
    }
}

//Check that super keyword isn't snagged here
class MyClassWithGenericSuperMethod
{
    void someMethod(java.util.List<? super java.util.Map<Object, Object>> l)
    {

    }

    /**
     * Not a valid clone override. Should not get flagged.
     * @param o some object
     * @return a cloned Object?
     */
    public static Object clone(Object o) {
        return null;
    }
}

class AnotherClass {

    /**
     * Not a valid clone override. Should not get flagged.
     * @param t some type
     * @param <T> a type
     * @return a cloned type?
     */
    public <T> T clone(T t) {
        return null;
    }
}

class NativeTest {
    public native Object clone(); // violation
}
