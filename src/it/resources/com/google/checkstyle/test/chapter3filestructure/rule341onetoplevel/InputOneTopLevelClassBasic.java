package com.google.checkstyle.test.chapter3filestructure.rule341onetoplevel;
public class InputOneTopLevelClassBasic
{
    public InputOneTopLevelClassBasic() throws CloneNotSupportedException
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

class NoSuperClone //warn
{
    public Object clone()
    {
        return null;
    }
}

class InnerClone //warn
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
class CloneWithTypeArguments //warn
{
    //Some code
}

class CloneWithTypeArgumentsAndNoSuper //warn
{
}

//Check that super keword isn't snagged here
class MyClassWithGenericSuperMethod //warn
{
    void someMethod(java.util.List<? super java.util.Map> l)
    {
        //Some code
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

class AnotherClass { //warn
    
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
