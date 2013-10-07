/*
 * InputInvalidTryIndent.java
 *
 * Created on December 31, 2002, 7:18 PM
 */

package com.puppycrawl.tools.checkstyle.indentation;

/**
 *
 * @author  jrichard
 */
public class InputInvalidTryIndent {
    
    /** Creates a new instance of InputInvalidTryIndent */
    public InputInvalidTryIndent() {
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
