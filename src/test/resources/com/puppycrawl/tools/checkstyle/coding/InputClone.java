package com.puppycrawl.tools.checkstyle.coding;
public class InputClone
{/* class body */
    public InputClone() throws CloneNotSupportedException
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
