/*
 * InputUseTabs.java
 *
 * Created on December 24, 2002, 10:28 PM
 */

package com.puppycrawl.tools.checkstyle.indentation;

/**
 *
 * @author  jrichard
 */
public class InputUseTwoSpaces {
    
  /** Creates a new instance of InputUseTabs */
  public InputUseTwoSpaces() {
    boolean test = true;
    if (test)
    {
      while (
        test == false) {
        System.exit(2);
      }
    }
     System.exit(3);
  }
    
}

class Test {
  public static void main(String[] args) {
    System.out.println(" Hello" +
      new Object() {
        public String toString() {
          return "World";
        }
      });

    new Object()
      .toString()
      .toString()
      .toString()
      .toString()
      .toString();
  }
}
