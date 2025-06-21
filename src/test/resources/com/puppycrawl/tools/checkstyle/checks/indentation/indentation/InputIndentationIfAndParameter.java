package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 2                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
class InputIndentationIfAndParameter { //indent:0 exp:0

  static String getString(int someInt, String someString) { //indent:2 exp:2
    return "String"; //indent:4 exp:4
  } //indent:2 exp:2

  static boolean conditionFirst(String longString, int //indent:2 exp:2
      integer, InnerClassFoo someInstance) { //indent:6 exp:6
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  static boolean conditionSecond(double longLongLongDoubleValue, //indent:2 exp:2
      String longLongLongString, String secondLongLongString) { //indent:6 exp:6
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  static boolean conditionThird(long veryLongValue) { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  static boolean conditionFourth(boolean flag) { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  static boolean getFifth(boolean flag1, boolean flag2) { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  static boolean co(boolean flag, //indent:2 exp:2
      Second7 instance) { //indent:6 exp:6
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  static boolean conNoArg() { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  static class InnerClassFoo { //indent:2 exp:2

    void fooMethodWithIf() { //indent:4 exp:4
      if (conditionFirst("Loooooooooooooooooong", new //indent:6 exp:6
          Second7("Loooooooooooooooooog"). //indent:10 exp:10
              get(new InputIndentationIfAndParameter(), "Log"), //indent:14 exp:>=10
                  new InnerClassFoo())) {} //indent:18 exp:>=10

      if (conditionSecond(10.0, new //indent:6 exp:6
          Second7("Lo" //indent:10 exp:10
              + "ong").getString(new InputIndentationIfAndParameter(), //indent:14 exp:14
              new Second7("log"). //indent:14 exp:16,18 warn
                  get(new InputIndentationIfAndParameter(), "")), "") //indent:18 exp:18
                      || conditionThird(2048) || conditionFourth(new //indent:22 exp:22
                          Second7("Loooooooo") //indent:26 exp:26
                          .gB(new InputIndentationIfAndParameter(), false)) || //indent:26 exp:>=10
                                  getFifth(true, new Second7(getString(2, "" //indent:34 exp:>=10
                                      + "oooong")).gB( //indent:38 exp:>=10
                  new InputIndentationIfAndParameter(), true)) //indent:18 exp:40,42 warn
                                              ||co(false,new //indent:46 exp:46
                                              Second7(getString(10, "Long" //indent:46 exp:>=10
                                                      + "F")))) {} //indent:54 exp:54
    } //indent:4 exp:4

    Object ann = new Object() { //indent:4 exp:4

      void fooMethodWithIf(String stringStringStringStringLooooongString, //indent:6 exp:6
          int intIntIntVeryLongNameForIntVariable, boolean //indent:10 exp:10
              fooooooooobooleanBooleanVeryLongName) { //indent:14 exp:>=10

        if (conditionFirst("Loooooooooooooooooong", new //indent:8 exp:8
            Second7("Loooooooooooooooooog"). //indent:12 exp:12
                get(new InputIndentationIfAndParameter(), ""), //indent:16 exp:>=12
                   new InnerClassFoo())) {} //indent:19 exp:>=12

        if (conditionSecond(10000000000.0, new //indent:8 exp:8
            Second7("Looooooooooooo" //indent:12 exp:12
                + "").getString(new InputIndentationIfAndParameter(), //indent:16 exp:16
                new Second7("long"). //indent:16 exp:18,20 warn
                get(new InputIndentationIfAndParameter(), "")), "") //indent:16 exp:16
                    || conditionThird(2048) || conditionFourth(new //indent:20 exp:20
                        Second7("Looooooooooooooo" //indent:24 exp:24
          + "").gB(new InputIndentationIfAndParameter(), false))|| //indent:10 exp:>=12 warn
                                getFifth(true, new Second7(getString(2, "" //indent:32 exp:>=12
                                    + "ooooooooooooooong")).gB( //indent:36 exp:>=12
                              new InputIndentationIfAndParameter(),true))//indent:30 exp:38,40 warn
                                            || co(false, new //indent:44 exp:44
                Second7(getString(10, "Long" //indent:16 exp:44 warn
                                                + "Foo<"))) || conNoArg()) {} //indent:48 exp:>=12
      } //indent:6 exp:6
    }; //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0
