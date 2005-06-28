package com.puppycrawl.tools.checkstyle.usage;

/** Test input for one method private field check */
public class InputOneMethodPrivateField
{
    private static int SFIELD0;
    private static int SFIELD1;
    private static int SFIELD2;
    private static int SFIELD3;
    
    private int mField0;
    private int mField1;
    private int mField2;
    private int mField3;
    private int mField4;
    private int mIgnore;
     
    public InputOneMethodPrivateField()
    {
        SFIELD0 = 0;
        mField0 = 0;
        mField3 = 0;
    }

    private void method()
    {
        SFIELD1 = 0;
        mField1 = 0;
        mField3++;
        mField4 = 0;
    }
    
    {
        SFIELD2 = 0;
        mField2 = 0;
        mField4 = 1;
    }
    
    static
    {
        SFIELD3 = 0;
    }
}

class Outer2
{
    private int mField0;
    private int mField1;
    private int mField2;
    private int mField3;
    
    private class Inner
    {
        public Inner()
        {
            int i  = mField0;
        }
        
        public void method()
        {
            mField1 = 0;
            mField3++;
        }
        
        {
            mField2 = 0;
        }
    }
    
    private void method()
    {
        mField3 = 0;
    }
}

class Outer3
{
    private int mField0;
    private int mField1;
    
    private void method()
    {
        final class Inner
        {
            public int mInner = mField0;
        }
    }
    
    /** tests NPE with dotted class name (bug 842781) */
     public void dotted()
     {
         java.lang.Thread th
             = new java.lang.Thread() {
                 public void run() {
                     ;
                 }
             };
         th.start();
     }
}

/** Tests NPE with foreach statements (bug 1109222) */
class Outer4
{
    private int[] mField;

    private void method()
    {
        for (int someInt : mField)
        {
            someInt = someInt ^ 2;
        }
    }
}