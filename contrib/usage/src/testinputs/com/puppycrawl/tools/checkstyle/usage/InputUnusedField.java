package com.puppycrawl.tools.checkstyle.usage;

import java.awt.Rectangle;

/** Test input for unused field check */
public class InputUnusedField
{
    private int mReadPrimitive;
    private int mPrimitive;
    private int mUnreadPrimitive;
    private String mReadString;
    private String mReadString2;
    private Rectangle mRectangle;
    private int[] mArray;
    private int[] mArray2;
    private int[] mUnreadArray;
    private int mThisPrimitive;
    
    private int mInitializer = 0;
    private int mUnused2 = mInitializer;
    
    private static final int SUNUSED = 0;
    private static int USED;

    static
    {
        USED = 0;    
    }
    
    public InputUnusedField()
    {
        int i = mReadPrimitive;
    }

    private void method()
    {
        int i = mReadString.length();
        "".equals(mReadString2);       
        int j = mRectangle.x;
        i = mArray[0];
        mArray2[0] = 0;
        this.mThisPrimitive = 1;
    }
    
    private void shadow()
    {
        int mUnreadPrimitive = 0;
        mUnreadPrimitive++;
        
        int[] mUnreadArray = {0};
        int i = mUnreadArray[0];
    }
    
    {
        mPrimitive = 0;
    }
}

class Outer
{
    private int mUsed1;
    private int mUsed2;
    private int mUsed3;
    private int mUnused;
    
    private class Inner
    {
        public Inner()
        {
            int i  = mUsed1;
        }
        
        public void method()
        {
            mUsed2 = 0;
        }
        
        {
            mUsed3 = 0;
        }
    }
}

class ParenthesizedExpression
{
    private int mUsed1 = 1;
    private int mUsed2 = 1;
    private boolean mUsed3 = true;
    
    public void testParentheses()
    {
        int x = 0;
        int a = (x++) / mUsed1;
        int b = (a - 1) << mUsed2;
        boolean c = (a == b) || mUsed3;
    }
}
