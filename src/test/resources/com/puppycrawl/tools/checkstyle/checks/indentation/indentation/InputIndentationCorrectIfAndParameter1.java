package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;                                                      //indent:0 exp:0

import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationIfAndParameter.co;              //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationIfAndParameter.getFifth;        //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationIfAndParameter.conditionFirst;  //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationIfAndParameter.conditionFourth; //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationIfAndParameter.conNoArg;        //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationIfAndParameter.conditionSecond; //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationIfAndParameter.conditionThird;  //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationIfAndParameter.getString;       //indent:0 exp:0

/**                                                                                                                          //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:                                                  //indent:1 exp:1
 *                                                                                                                           //indent:1 exp:1
 * arrayInitIndent = 4                                                                                                       //indent:1 exp:1
 * basicOffset = 2                                                                                                           //indent:1 exp:1
 * braceAdjustment = 0                                                                                                       //indent:1 exp:1
 * caseIndent = 4                                                                                                            //indent:1 exp:1
 * forceStrictCondition = false                                                                                              //indent:1 exp:1
 * lineWrappingIndentation = 4                                                                                               //indent:1 exp:1
 * tabWidth = 4                                                                                                              //indent:1 exp:1
 * throwsIndent = 4                                                                                                          //indent:1 exp:1
 *                                                                                                                           //indent:1 exp:1
 *                                                                                                                           //indent:1 exp:1
 */                                                                                                                          //indent:1 exp:1
public class InputIndentationCorrectIfAndParameter1 {                                                                        //indent:0 exp:0

  void fooMethodWithIf() {                                                                                                   //indent:2 exp:2

    if (conditionFirst("Loooooooooooooooooong", new                                                                          //indent:4 exp:4
        Second7("Loooooooooooooooooog").                                                                                     //indent:8 exp:8
            get(new InputIndentationIfAndParameter(), "Log"),                                                                //indent:12 exp:12
                new InputIndentationIfAndParameter.InnerClassFoo())) {}                                                      //indent:16 exp:16

    if (conditionSecond(10000000000.0, new                                                                                   //indent:4 exp:4
        Second7("Looooooooooooo"                                                                                             //indent:8 exp:8
            + "ooog").getString(new InputIndentationIfAndParameter(),                                                        //indent:12 exp:12
            new Second7("loooooooooong").                                                                                    //indent:12 exp:14,16 warn
                    get(new InputIndentationIfAndParameter(), "long")), "long")                                              //indent:20 exp:20
                        || conditionThird(2048) || conditionFourth(new                                                       //indent:24 exp:24
                            Second7("Looooooo"                                                                               //indent:28 exp:28
                + "").gB(new InputIndentationIfAndParameter(), false)) ||                                                    //indent:16 exp:28 warn
                                    getFifth(true, new Second7(getString(2, ""                                               //indent:36 exp:36
                                        + "oooong")).gB(                                                                     //indent:40 exp:40
                        new InputIndentationIfAndParameter(), true))                                                         //indent:24 exp:42,44 warn
                                            || co(false, new                                                                 //indent:44 exp:44
                                                Second7(getString(1, ""                                                      //indent:48 exp:48
                                                    + "Foo><"))) || conNoArg()                                               //indent:52 exp:52
                                                        || conNoArg()) {}                                                    //indent:56 exp:56
  }                                                                                                                          //indent:2 exp:2
}                                                                                                                            //indent:0 exp:0

class Second7 {                                                                                                              //indent:0 exp:0

  public Second7(String string) {                                                                                            //indent:2 exp:2

  }                                                                                                                          //indent:2 exp:2

  String getString(InputIndentationIfAndParameter instance, int integer) {                                                   //indent:2 exp:2
    return "String";                                                                                                         //indent:4 exp:4
  }                                                                                                                          //indent:2 exp:2

  int get(InputIndentationIfAndParameter instance, String string) {                                                          //indent:2 exp:2
    return -1;                                                                                                               //indent:4 exp:4
  }                                                                                                                          //indent:2 exp:2

  boolean gB(InputIndentationIfAndParameter instance, boolean flag){                                                         //indent:2 exp:2
    return false;                                                                                                            //indent:4 exp:4
  }                                                                                                                          //indent:2 exp:2

  Second7 getInstance() {                                                                                                    //indent:2 exp:2
    return new Second7("VeryLoooooooooo"                                                                                     //indent:4 exp:4
        + "oongString");                                                                                                     //indent:8 exp:8
  }                                                                                                                          //indent:2 exp:2
}                                                                                                                            //indent:0 exp:0
