/*
 * ValidIndent.java
 *
 * Created on November 6, 2002, 9:39 PM
 */

package com.puppycrawl.tools.checkstyle.indentation;

/**
 *
 * @author  jrichard
 */
public class InputInvalidIfIndent {
        
    // ctor with rcurly on same line
    public InputInvalidIfIndent() {
    }

    // ctor with rcurly on next line
    public InputInvalidIfIndent(int dummy)
    {
    }

    // method with rcurly on same line
    public void method() {
    }

    // method with rcurly on next line
    public void method2()
    {
    }
    
    // method with a bunch of params
    public void method2(int x, int y, int w, int h)
    {
    }
    
    // params on multiple lines
    public void method2(int x, int y, int w, int h,
        int x1, int y1, int w1, int h1)
    {
    }

    // test ifs
    public void emptyIfTest()
    {
        boolean test = true;
                
        // lcurly on same line
 if (test) {
        }
        
        // lcurly on next line -- if, rcurly indented too far, lcurly not far enough
        // 
         if (test) 
         {
       }
        
      if (test) 
     {
     }
                
        // lcurly for if and else on same line -- too much space after if on same line -- ALLOWED
        if (test)      {
          } else {      // this is not allowed
       }
        
        // lcurly for if and else on same line
         if (test) 
       {
        } 
         else 
        {
         }

        // lcurly for if and else on same line -- mixed braces
          if (test) {
       } 
         else 
       {
         }
        

        // lcurly for if and else on same line -- mixed braces
         if (test) 
         {
         } else 
       {
          }
        
        // lcurly for if and else on same line -- mixed braces
      if (test) 
          {
          } else {
       }
        
        // lcurly for if and else on same line -- mixed braces, unnested
     if (test) {
           } 
     else {
           }
    }

    /////  same as above, with statements
    public void  populatedIfTest()
    {
        boolean test = false;
        // no braces if
        if (test)
              System.getProperty("blah");

        // no braces if/else
        if (test)
            System.getProperty("blah");
        else
            System.getProperty("blah");

        
        // lcurly on same line, and stmt
        if (test) {
              System.getProperty("blah");
        }
        
        // lcurly on next line and stmt
        if (test) 
          {
          System.getProperty("blah");
        }
        // lcurly for if and else on same line
        if (test) {

              System.
          getProperty("blah");
        } else {
          System.
        getProperty("blah");
        }
        
        // lcurly for if and else on same line
        if (test) 
        {
            System.getProperty("blah");
                System.getProperty("blah");
         } 
        else 
        {
                System.getProperty("blah");
            System.getProperty("blah");
        }

        // lcurly for if and else on same line -- mixed braces
        if (test) {
System.getProperty("blah");
        } 
        else 
        {
                                        System.getProperty("blah");
        }
        

        // lcurly for if and else on same line -- mixed braces
        if (test) 
        {
              System.getProperty("blah");
        } else 
        {
              System.getProperty("blah");
        }
        
        // lcurly for if and else on same line -- mixed braces
        if (test) 
        {
          System.getProperty("blah");
        } else {
          System.getProperty("blah");
        }
        
        // lcurly for if and else on same line -- mixed braces, unnested
          if (test) {
              System.getProperty("blah");
          } 
          else {
              System.getProperty("blah");
          }
        
        if (test
         && 7 < 8 && 8 < 9
           && 10 < 11) {
        }
        
        if (test)
          return;
        
        if (test) {
       } else if (7 < 8) {
        } else if (8 < 9) {
        }

        if (test) {
            System.getProperty("blah"); 
        } else if (7 < 8) {
          System.getProperty("blah"); 
        } else if (8 < 9) {
          System.getProperty("blah"); 
        }

        
        if (test)
            System.getProperty("blah"); 
        else if (7 < 8)
          System.getProperty("blah"); 
        else if (8 < 9)
            System.getProperty("blah"); 
        
        
        // TODO: bother to support this style?
        if (test) {
            System.getProperty("blah"); 
        } else 
          if (7 < 8) {
                System.getProperty("blah"); 
            } else 
                if (8 < 9) {
                  System.getProperty("blah"); 
                }
        
        if (test) {
            System.getProperty("blah"); }
    }
    
    public void  parenIfTest() {
        boolean test = true;
        
        if (test
          ) {
            System.getProperty("blah"); 
        }

        if (test
      ) 
        {
            System.getProperty("blah"); 
        }
        
        if 
      (
            test
      ) {
            System.getProperty("blah"); 
        }
        
    }
    
}
