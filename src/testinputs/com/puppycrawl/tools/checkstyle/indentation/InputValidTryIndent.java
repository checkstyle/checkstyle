/*
 * InputValidTryIndent.java
 *
 * Created on December 31, 2002, 6:56 PM
 */

package com.puppycrawl.tools.checkstyle.indentation;

/**
 *
 * @author  jrichard
 */
public class InputValidTryIndent {
    
    /** Creates a new instance of InputValidTryIndent */
    public InputValidTryIndent() {
    }
    
    public void method() {
        
        try {
        } catch (Throwable t) {
            System.out.println("err");
        }

        try {
            System.out.println("test");
        } finally {
            System.out.println("finally");
        }

        try {
        } catch (Throwable t) {
            System.out.println("err");
        } finally {
        }

        try {
        } catch (Exception t) {
            System.out.println("err");
        } catch (Throwable t) {
            System.out.println("err");
        }

        try {
        } catch (Exception t) {
        } catch (Throwable t) {
        }

        
        try {
            System.out.println("try");
        } 
        catch (Exception t) {
            System.out.println("err");
            System.out.println("err");
        } 
        catch (Throwable t) {
            System.out.println("err");
        } 
        finally {
        }
        
        try 
        {
            System.out.println("try");
        } 
        catch (Exception t) 
        {
            System.out.println("err");
            System.out.println("err");
        } 
        catch (Throwable t) 
        {
            System.out.println("err");
        }
        finally 
        {
        }
        
        
    }
    
}
