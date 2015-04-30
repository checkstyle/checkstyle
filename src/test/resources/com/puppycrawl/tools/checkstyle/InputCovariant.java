package com.puppycrawl.tools.checkstyle;

/**
 * Test file for covariant equals methods.
 * @author Rick Giles
 */
public class InputCovariant
{
    private class Inner
    {
        public boolean equals(Inner aInner)
        {
            return false;
        }
    }

    private class Inner2
    {
        public boolean equals(Inner2 aInner2)
        {
            return false;
        }
        
        public boolean equals(Object aObj)
        {
            return false;       
        }
    }

    public boolean equals(InputCovariant aInputCovariant)
    {
        return false;
    }
}

class InputCovariant2
{   
    public boolean equals(InputCovariant2 aInputCovariant2)
    {
        return false;
    }
    
    public boolean equals(Object aObject)
    {
        return false;
    }
}

class InputCovariant3
{   
    public boolean equals(InputCovariant3 aInputCovariant3)
    {
        return false;
    }
    
    public boolean equals(java.lang.Object aObject)
    {
        return false;
    }
}

class InputCovariant4
{
    public boolean equals(int i)
    {
        return false;
    }
}

class Anonymous
{
    Comparable comp = new Comparable()
        {
            public int compareTo(Object aObject)
            {
                return 0;
            }
            public boolean equals(String aString)
            {
                return false;
            }
        };
    
    public boolean equals(Object aObject)
    {
        return false;
    }
    
    public void method()
    {
        Double d = new Double(1);
    }
}

abstract class InputCovariant5
{
    public abstract boolean equals(InputCovariant4 aInputCovariant4);
}

interface InputCovariant6
{
    public boolean equals(InputCovariant5 aInputCovariant5);
}

class InputGenericCovariant7
{
    public <A> boolean equals(InputGenericCovariant7 aInputCovariant7)
    {
        return true;
    }

    public boolean equals(Object aObject)
    {
        return false;
    }
}

class InputGenericCovariant8
{
    public <A> boolean equals(InputGenericCovariant8 aInputCovariant8)
    {
        return true;
    }

    public boolean equals(Object aObject)
    {
        return false;
    }
}

enum InputEnumCovariant {
    EQUALS;

    public boolean equals(InputEnumCovariant inputEnumCovariant) {
        return false;
    }

    int equals(Integer integer) {
        return 0;
    }
}