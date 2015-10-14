package com.puppycrawl.tools.checkstyle.checks.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 2                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 2                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * @author  jrichard                                                         //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputUseTwoSpaces { //indent:0 exp:0

  /** Creates a new instance of InputUseTabs */ //indent:2 exp:2
  public InputUseTwoSpaces() { //indent:2 exp:2
    boolean test = true; //indent:4 exp:4
    if (test) //indent:4 exp:4
    { //indent:4 exp:4
      while ( //indent:6 exp:6
        test == false) { //indent:8 exp:8
        System.exit(2); //indent:8 exp:8
      } //indent:6 exp:6
    } //indent:4 exp:4
     System.exit(3); //indent:5 exp:4 warn
  } //indent:2 exp:2

} //indent:0 exp:0

class Test { //indent:0 exp:0
  public static void main(String[] args) { //indent:2 exp:2
    System.out.println(" Hello" + //indent:4 exp:4
      new Object() { //indent:6 exp:>=6
        public String toString() { //indent:8 exp:8
          return "World"; //indent:10 exp:10
        } //indent:8 exp:8
      }); //indent:6 exp:6

    new Object() //indent:4 exp:4
      .toString() //indent:6 exp:>=6
      .toString() //indent:6 exp:>=6
      .toString() //indent:6 exp:>=6
      .toString() //indent:6 exp:>=6
      .toString(); //indent:6 exp:>=6
  } //indent:2 exp:2
} //indent:0 exp:0
