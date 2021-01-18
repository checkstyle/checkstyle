/* Config:                                                                    //indent:0 exp:0
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 2                                                        //indent:1 exp:1
 * basicOffset = 2                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 2                                                             //indent:1 exp:1
 * forceStrictCondition = true                                                //indent:1 exp:1
 * lineWrappingIndentation = 2                                                //indent:1 exp:1
 * tabWidth = 2                                                               //indent:1 exp:1
 * throwsIndent = 2                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationInvalidWhileIndentStrict { //indent:0 exp:0
  public static void indent() { //indent:2 exp:2
    boolean a = false; //indent:4 exp:4
    boolean b = false; //indent:4 exp:4
    boolean c = false; //indent:4 exp:4

    while (a || //indent:4 exp:4
                    b || //indent:20 exp:6 warn
                 c) { //indent:17 exp:6 warn
      a = true; //indent:6 exp:6
    } //indent:4 exp:4

    if (a || //indent:4 exp:4
                    b || //indent:20 exp:6 warn
                 c) { //indent:17 exp:6 warn
      a = true; //indent:6 exp:6
    } //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0
