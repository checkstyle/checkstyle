/*
 * InputValidSwitchIndent.java
 *
 * Created on November 27, 2002, 11:40 PM
 */

package com.puppycrawl.tools.checkstyle.indentation;

/**
 *
 * @author  jrichard
 */
public class InputInvalidSwitchIndent {
    
    private static final int CONST = 5;
    private static final int CONST2 = 2;
    private static final int CONST3 = 3;
    
    /** Creates a new instance of InputInvalidSwitchIndent */
    public InputInvalidSwitchIndent() {
    }
    
    private void method1() {
        int s = 3;
        
      switch (s) {
            
          case 4:
              System.out.println("");
                break;

            case CONST:
              break;

              case CONST2:
          case CONST3:
                break;

          default:
              System.out.println("");
              break;
        }
        

        // some people like to add curlys to their cases:
        switch (s) {
            
            case 4: {
              System.out.println("");
                  break;
          }

            case CONST2:
            case CONST3:
          {
                System.out.println("");
                break;
              }

            
            case 22:
              {
                System.out.println("");
                break;
          }            
        }
         
        // check broken 'case' lines
        switch (s) {
            
            case 
              CONST:
                break;

            case CONST2:
            case 
              CONST3:
            {
                System.out.println("");
                break;
            }
        }        

        switch (s) {
      }

        switch (s) 
      {
          }
        switch (s) 
          {
      }
        
    }
    
}
