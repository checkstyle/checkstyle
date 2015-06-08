package com.google.checkstyle.test.chapter3filestructure.rule341onetoplevel;
public class OneTopLevelClassInputGood //ok
{
    public OneTopLevelClassInputGood() throws CloneNotSupportedException
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
