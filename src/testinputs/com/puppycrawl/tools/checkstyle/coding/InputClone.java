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