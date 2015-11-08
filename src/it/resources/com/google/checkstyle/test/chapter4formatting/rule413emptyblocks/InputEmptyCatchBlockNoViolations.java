////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
////////////////////////////////////////////////////////////////////////////////
package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

import java.io.IOException;

public class InputEmptyCatchBlockNoViolations
{
    private void foo6() {
        try {
            throw new IOException();
        } catch (IOException expected) { // This is expected
            int k = 0;
        }
    }
    
    public void testTryCatch()
    {
        try {
            int y=0;
            int u=8;
            int e=u-y;
            return; 
        } 
        catch (Exception e) {
            System.out.println(e);
            return; 
        }
        finally
        {
            return; 
        }
    }
    
    public void testTryCatch3()
    {
        try {
            int y=0;
            int u=8;
            int e=u-y;
        } 
        catch (IllegalArgumentException e) {
            System.out.println(e); //some comment
            return; 
        }
        catch (IllegalStateException ex) {
                System.out.println(ex);
                return; 
        }
    }
    
    public void testTryCatch4()
    {
        int y=0;
        int u=8;
        try {
            int e=u-y;
        } 
        catch (IllegalArgumentException e) {
            System.out.println(e);
            return; 
        }
    }
    public void setFormats() {
        try {
            int k = 4;
        } catch (Exception e) {
            Object k = null;
            if (k != null)
                k = "ss";
            else {
                return; 
            }
        }
    }
}
