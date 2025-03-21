//non-compiled with javac: Compilable with Java17                            //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;       //indent:0 exp:0

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


public class InputIndentationMultilineStatements {                     //indent:0 exp:0

  public boolean test1(int k) {                                        //indent:2 exp:2
    return (k < 10)                                                    //indent:4 exp:4
      && k > 0;                                                        //indent:6 exp:8 warn
  }                                                                    //indent:2 exp:2

  public boolean test1Correct(int k) {                                 //indent:2 exp:2
    return (k < 10)                                                    //indent:4 exp:4
        && k > 0;                                                      //indent:8 exp:8
  }                                                                    //indent:2 exp:2

  void test2(boolean result) {                                         //indent:2 exp:2
    int collectResult = result ?                                       //indent:4 exp:4
        0:                                                             //indent:8 exp:8
        1;                                                             //indent:8 exp:8
  }                                                                    //indent:2 exp:2

  void test2Incorrect(boolean result) {                                //indent:2 exp:2
    int collectResult = result ?                                       //indent:4 exp:4
      0:                                                               //indent:6 exp:8 warn
      1;                                                               //indent:6 exp:8 warn
  }                                                                    //indent:2 exp:2

  int testIfConditionMultiline(int newState, int tableName) {          //indent:2 exp:2
    int flag = 0;                                                      //indent:4 exp:4
    if (                                                               //indent:4 exp:4
        (newState == 10) &&                                            //indent:8 exp:8
            tableName == 1 &&  flag > 0 ||                             //indent:12 exp:12
                (newState != 0 &&                                      //indent:16 exp:16
                    flag < 0 && tableName == 0)) {                     //indent:20 exp:20
      flag = 1;                                                        //indent:6 exp:6
    }                                                                  //indent:4 exp:4
    return flag;                                                       //indent:4 exp:4
  }                                                                    //indent:2 exp:2

  private String test5() {                                             //indent:2 exp:2
    //below indent:4 exp:4
    final String simpleProperty = """
        s                                                              //indent:8 exp:8
        """;                                                           //indent:8 exp:8

    //below indent:4 exp:4
    return ("""
      def newInstance = params.instance;                               //indent:6 exp:6
      def existingInstance = ctx._source;                              //indent:6 exp:6
      """                                                              //indent:6 exp:8 warn
        + simpleProperty);                                             //indent:8 exp:8
  }                                                                    //indent:2 exp:2

  private String test6() {                                             //indent:2 exp:2
    //below indent:4 exp:4
    final String simplePropertyUpdateScript = """
        s                                                              //indent:8 exp:8
        """;                                                           //indent:8 exp:8

    //below indent:4 exp:4
    return ("""
        def newInstance = params.instance;                             //indent:8 exp:8
        def existingInstance = ctx._source;                            //indent:8 exp:8
        """                                                            //indent:8 exp:8
        + simplePropertyUpdateScript);                                 //indent:8 exp:8
  }                                                                    //indent:2 exp:2
}                                                                      //indent:0 exp:0
