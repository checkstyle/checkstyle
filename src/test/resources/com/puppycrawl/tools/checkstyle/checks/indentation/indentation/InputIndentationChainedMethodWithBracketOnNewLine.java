package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;    //indent:0 exp:0

/**                                                                        //indent:0 exp:0
 * This test-input is intended to be checked using following configuration://indent:1 exp:1
 *                                                                         //indent:1 exp:1
 * arrayInitIndent = 2                                                     //indent:1 exp:1
 * basicOffset = 2                                                         //indent:1 exp:1
 * braceAdjustment = 0                                                     //indent:1 exp:1
 * caseIndent = 2                                                          //indent:1 exp:1
 * forceStrictCondition = false                                            //indent:1 exp:1
 * lineWrappingIndentation = 4                                             //indent:1 exp:1
 * tabWidth = 2                                                            //indent:1 exp:1
 * throwsIndent = 2                                                        //indent:1 exp:1
 *                                                                         //indent:1 exp:1
 *                                                                         //indent:1 exp:1
 */                                                                        //indent:1 exp:1

public class InputIndentationChainedMethodWithBracketOnNewLine {           //indent:0 exp:0

  InputIndentationChainedMethodWithBracketOnNewLine indentation(           //indent:2 exp:2
      Object... args) {                                                    //indent:6 exp:6
    return this;                                                           //indent:4 exp:4
  }                                                                        //indent:2 exp:2

  InputIndentationChainedMethodWithBracketOnNewLine thenReturn(            //indent:2 exp:2
      InnerClass innerClass) {                                             //indent:6 exp:6
    return this;                                                           //indent:4 exp:4
  }                                                                        //indent:2 exp:2

  static InputIndentationChainedMethodWithBracketOnNewLine when() {        //indent:2 exp:2
    return new InputIndentationChainedMethodWithBracketOnNewLine();        //indent:4 exp:4
  }                                                                        //indent:2 exp:2

  public static void main(String[] args) {                                 //indent:2 exp:2
    InputIndentationChainedMethodWithBracketOnNewLine i =                  //indent:4 exp:4
        new InputIndentationChainedMethodWithBracketOnNewLine();           //indent:8 exp:8
    i.indentation()                                                        //indent:4 exp:4
        .indentation(                                                      //indent:8 exp:8
            "a",                                                           //indent:12 exp:12
            "b"                                                            //indent:12 exp:12
        );                                                                 //indent:8 exp:8

    i.indentation()                                                        //indent:4 exp:4
      .indentation(                                                        //indent:6 exp:8 warn
        "a",                                                               //indent:8 exp:10 warn
          "b"                                                              //indent:10 exp:10
      );                                                                   //indent:6 exp:8 warn
    when()                                                                 //indent:4 exp:4
        .thenReturn(                                                       //indent:8 exp:8
            new InnerClass("response", "{\n" +                             //indent:12 exp:12
                                       "  \"query\": \"someValue\"\n" +    //indent:39 exp:39
                                       "}")                                //indent:39 exp:39
        );                                                                 //indent:8 exp:8
    when()                                                                 //indent:4 exp:4
        .thenReturn(                                                       //indent:8 exp:8
            new InnerClass("response", "")                                 //indent:12 exp:12
        );                                                                 //indent:8 exp:8
    String string1 =                                                       //indent:4 exp:4
        foo("fooooooooooooooo", 0, false);                                 //indent:8 exp:>=8
    String string2 =                                                       //indent:4 exp:4
     foo("fooooooooooooooo", 0, false);                                 //indent:5 exp:>=8 warn
    when().indentation(new String("foo"),                                  //indent:4 exp:4
                       "bar");                                             //indent:23 exp:>=8
    when().                                                                //indent:4 exp:4
        indentation("a","b");                                              //indent:8 exp:8
    when().indentation("a")                                                //indent:4 exp:4
        .indentation("b")                                                  //indent:8 exp:8
        .indentation("c");                                                 //indent:8 exp:8
  }                                                                        //indent:2 exp:2

  static String foo (String aStr,                                          //indent:2 exp:2
        int aNnum, boolean aFlag) {                                        //indent:8 exp:>=6

    if (true && true &&                                                    //indent:4 exp:4
             true && true) {                                               //indent:13 exp:>=8

      String string2 = foo("fooooooo"                                      //indent:6 exp:6
              + "oooooooo", 0, false);                                     //indent:14 exp:>=10
      if (false &&                                                         //indent:6 exp:6
              false && false) {                                            //indent:14 exp:>=10

    }                                                                      //indent:4 exp:6 warn
    }                                                                      //indent:4 exp:4
  return "string";                                                         //indent:2 exp:4 warn
  }                                                                        //indent:2 exp:2

  public static class InnerClass {                                         //indent:2 exp:2
    public InnerClass(String param1, String param2) {                      //indent:4 exp:4
    }                                                                      //indent:4 exp:4
  }                                                                        //indent:2 exp:2
}                                                                          //indent:0 exp:0
