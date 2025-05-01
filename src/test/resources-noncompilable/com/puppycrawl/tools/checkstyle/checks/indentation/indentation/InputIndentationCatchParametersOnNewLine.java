// non-compiled with javac: Compilable with Java15                            //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 2                                                        //indent:1 exp:1
 * basicOffset = 2                                                            //indent:1 exp:1
 * braceAdjustment = 2                                                        //indent:1 exp:1
 * caseIndent = 2                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;       //indent:0 exp:0

public class InputIndentationCatchParametersOnNewLine {                     //indent:0 exp:0
  public void test1() {                                                       //indent:2 exp:2
    try {                                                                     //indent:4 exp:4
      System.out.println("try0");                                             //indent:6 exp:6
    } catch (NullPointerException                                             //indent:4 exp:4
| IllegalArgumentException t) {                                              //indent:0 exp:8 warn
    }                                                                         //indent:4 exp:4
  }                                                                           //indent:2 exp:2

  void test2() {                                                              //indent:2 exp:2
    try {                                                                     //indent:4 exp:4
      System.out.println("try");                                              //indent:6 exp:6
    } catch (                                                                 //indent:4 exp:4
        @SuppressWarnings("PMD.AvoidCatchingGenericException")                //indent:8 exp:8
    Exception e) {                                                            //indent:4 exp:8 warn
      java.util.logging.Logger.getAnonymousLogger().severe(e.toString());     //indent:6 exp:6
    }                                                                         //indent:4 exp:4

    try {                                                                     //indent:4 exp:4
      System.out.println("try1");                                             //indent:6 exp:6
    } catch (                                                                 //indent:4 exp:4
    @SuppressWarnings("PMD.AvoidCatchingGenericException")                  //indent:4 exp:8 warn
        Exception e) {                                                        //indent:8 exp:8
      java.util.logging.Logger.getAnonymousLogger().severe(e.toString());     //indent:6 exp:6
    }                                                                         //indent:4 exp:4
  }                                                                           //indent:2 exp:2

  void test3() {                                                              //indent:2 exp:2
    try {                                                                     //indent:4 exp:4
      System.out.println("try");                                              //indent:6 exp:6
    } catch (                                                                 //indent:4 exp:4
            @SuppressWarnings("PMD.AvoidCatchingGenericException")          //indent:12 exp:8 warn
        Exception e) {                                                        //indent:8 exp:8
      java.util.logging.Logger.getAnonymousLogger().severe(e.toString());     //indent:6 exp:6
    }                                                                         //indent:4 exp:4
  }                                                                           //indent:2 exp:2

  void test4() {                                                              //indent:2 exp:2
    try {                                                                     //indent:4 exp:4
      System.out.println("try");                                              //indent:6 exp:6
    } catch (NullPointerException                                             //indent:4 exp:4
        | IllegalArgumentException t) {                                       //indent:8 exp:8
    }                                                                         //indent:4 exp:4
    try {                                                                     //indent:4 exp:4
      System.out.println("try");                                              //indent:6 exp:6
    } catch (                                                                 //indent:4 exp:4
        NullPointerException                                                  //indent:8 exp:8
        | IllegalArgumentException t) {                                     //indent:8 exp:12 warn
    }                                                                         //indent:4 exp:4
  }                                                                           //indent:2 exp:2

  private String test5() {                                             //indent:2 exp:2
    final String simpleProperty = "";                                         //indent:4 exp:4
    return """ //indent:4 exp:4
      def newInstance = params.instance; //indent:6 exp:6
      def existingInstance = ctx._source; //indent:6 exp:6
      """                                                                     //indent:6 exp:8 warn
        + simpleProperty;                                                     //indent:8 exp:8
  }                                                                           //indent:2 exp:2

  private String test6() {                                             //indent:2 exp:2
    final String simplePropertyUpdateScript = "";                             //indent:4 exp:4
    return """ //indent:4 exp:4
        def newInstance = params.instance; //indent:8 exp:8
        def existingInstance = ctx._source; //indent:8 exp:8
        """                                                                   //indent:8 exp:8
        + simplePropertyUpdateScript;                                         //indent:8 exp:8
  }                                                                           //indent:2 exp:2
}                                                                             //indent:0 exp:0
