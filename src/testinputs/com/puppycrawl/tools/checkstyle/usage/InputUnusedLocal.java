package com.puppycrawl.tools.checkstyle.usage;

import java.awt.Rectangle;

/** Test input for unused local variable check */
public class InputUnusedLocal
{
    private int mUnreadPrimitive = 0;
    
    public InputUnusedLocal()
    {
        int readPrimitive = 0;
        int mUnreadPrimitive = 0;
        int i = readPrimitive;
        i++;
       
       this.mUnreadPrimitive++; 
    }

    private void method()
    {
        String readObject = "";
        Rectangle rectangle = null;
        Object unreadObject;
        int i = readObject.length();
        
        int j = rectangle.x;
        
        i += j;
        }

    private void methodArrays()
    {
        int[] array = {};
        int[] array2 = {};
        int[] unreadArray;
        int i = array[0];
        array2[0] = 0;
        i++;
    }
    
    /** tests that neither type nor typecast are considered to be a reference */
    public void method2()
    {
        int java;
        java.io.File file = (java.io.File) null;
        if (file != null) {
        }       
    }
    
    /** tests array index references */
    public void testArrayIndex()
    {
        int [][][] a = new int[1][1][1];
        int i = 0;
        int j = 0;
        int k = 0;
        a[i][j][k]++;
    }
}
