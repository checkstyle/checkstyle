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
class InputIndentationCorrectIfAndParameter { //indent:0 exp:0

  String getString(int someInt, String someString) { //indent:2 exp:2
    return "String"; //indent:4 exp:4
  } //indent:2 exp:2

  void fooMethodWithIf() { //indent:2 exp:2

    if (conditionFirst("Loooooooooooooooooong", new //indent:4 exp:4
        SecondClassLongNam7("Loooooooooooooooooog"). //indent:8 exp:8
        getInteger(new InputIndentationCorrectIfAndParameter(), "Looooog"), //indent:8 exp:8
        new InnerClassFoo())) {} //indent:8 exp:8

    if (conditionSecond(10000000000.0, new //indent:4 exp:4
        SecondClassLongNam7("Looooooooooooo" //indent:8 exp:8
        + "oooooooooooong").getString(new InputIndentationCorrectIfAndParameter(), //indent:8 exp:8
        new SecondClassLongNam7("loooooooooong"). //indent:8 exp:8
        getInteger(new InputIndentationCorrectIfAndParameter(), "long")), "loong") //indent:8 exp:8
        || conditionThird(2048) || conditionFourth(new //indent:8 exp:8
        SecondClassLongNam7("Looooooooooooooo" //indent:8 exp:8
        + "oo").gB(new InputIndentationCorrectIfAndParameter(), false)) || //indent:8 exp:8
        conditionFifth(true, new SecondClassLongNam7(getString(2048, "Looo" //indent:8 exp:8
        + "ooooooooooooooooooooooooooooooooooooooooooong")).gB( //indent:8 exp:8
        new InputIndentationCorrectIfAndParameter(), true)) || co(false, new //indent:8 exp:8
        SecondClassLongNam7(getString(100000, "Loooooong" //indent:8 exp:8
        + "Fooooooo><"))) || conditionNoArg() //indent:8 exp:8
        || conditionNoArg() || //indent:8 exp:8
        conditionNoArg() || conditionNoArg()) {} //indent:8 exp:8
  } //indent:2 exp:2

  private boolean conditionFirst(String longString, int //indent:2 exp:2
      integer, InnerClassFoo someInstance) { //indent:6 exp:6
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  private boolean conditionSecond(double longLongLongDoubleValue, //indent:2 exp:2
      String longLongLongString, String secondLongLongString) { //indent:6 exp:6
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  private boolean conditionThird(long veryLongValue) { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  private boolean conditionFourth(boolean flag) { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  private boolean conditionFifth(boolean flag1, boolean flag2) { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  private boolean co(boolean flag, //indent:2 exp:2
      SecondClassLongNam7 instance) { //indent:6 exp:6
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  private boolean conditionNoArg() { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  class InnerClassFoo { //indent:2 exp:2

    void fooMethodWithIf() { //indent:4 exp:4
      if (conditionFirst("Loooooooooooooooooong", new //indent:6 exp:6
          SecondClassLongNam7("Loooooooooooooooooog"). //indent:10 exp:10
              getInteger(new InputIndentationCorrectIfAndParameter(), "Log"), //indent:14 exp:>=10
              new InnerClassFoo())) {} //indent:14 exp:>=10

      if (conditionSecond(10000000000.0, new //indent:6 exp:6
          SecondClassLongNam7("Looooooooooooo" //indent:10 exp:10
          + "ooooooong").getString(new InputIndentationCorrectIfAndParameter(), //indent:10 exp:10
          new SecondClassLongNam7("loooooooooong"). //indent:10 exp:10
          getInteger(new InputIndentationCorrectIfAndParameter(), "long")), "l") //indent:10 exp:10
          || conditionThird(2048) || conditionFourth(new //indent:10 exp:10
          SecondClassLongNam7("Looooooooooooooo" //indent:10 exp:10
            + "og").gB(new InputIndentationCorrectIfAndParameter(), false)) || //indent:12 exp:>=10
            conditionFifth(true, new SecondClassLongNam7(getString(2048, "Loo" //indent:12 exp:>=10
            + "ooooooooooooooooooooooooooooooooooooooooooong")).gB( //indent:12 exp:>=10
            new InputIndentationCorrectIfAndParameter(), true)) ||co(false,new //indent:12 exp:>=10
              SecondClassLongNam7(getString(100000, "Loooooong" //indent:14 exp:>=10
              + "Fooooooo><"))) || conditionNoArg() //indent:14 exp:>=10
              || conditionNoArg() || //indent:14 exp:>=10
              conditionNoArg() || conditionNoArg()) {} //indent:14 exp:>=10
    } //indent:4 exp:4

    Object ann = new Object() { //indent:4 exp:4

      void fooMethodWithIf(String stringStringStringStringLooooongString, //indent:6 exp:6
          int intIntIntVeryLongNameForIntVariable, boolean //indent:10 exp:10
              fooooooooobooleanBooleanVeryLongName) { //indent:14 exp:>=10

        if (conditionFirst("Loooooooooooooooooong", new //indent:8 exp:8
            SecondClassLongNam7("Loooooooooooooooooog"). //indent:12 exp:12
                getInteger(new InputIndentationCorrectIfAndParameter(), "Lg"), //indent:16 exp:>=12
                   new InnerClassFoo())) {} //indent:19 exp:>=12

        if (conditionSecond(10000000000.0, new //indent:8 exp:8
            SecondClassLongNam7("Looooooooooooo" //indent:12 exp:12
            + "oooooong").getString(new InputIndentationCorrectIfAndParameter(), //indent:12 exp:12
            new SecondClassLongNam7("loooooooooong"). //indent:12 exp:12
            getInteger(new InputIndentationCorrectIfAndParameter(), "lg")), "l") //indent:12 exp:12
            || conditionThird(2048) || conditionFourth(new //indent:12 exp:12
            SecondClassLongNam7("Looooooooooooooo" //indent:12 exp:12
              + "o").gB(new InputIndentationCorrectIfAndParameter(), false))|| //indent:14 exp:>=12
              conditionFifth(true, new SecondClassLongNam7(getString(2048, "L" //indent:14 exp:>=12
              + "ooooooooooooooooooooooooooooooooooooooooooong")).gB( //indent:14 exp:>=12
              new InputIndentationCorrectIfAndParameter(),true))||co(false,new //indent:14 exp:>=12
                SecondClassLongNam7(getString(100000, "Loooooong" //indent:16 exp:>=12
                + "Fooooooo><"))) || conditionNoArg() //indent:16 exp:>=12
                || conditionNoArg() || //indent:16 exp:>=12
                  conditionNoArg() || conditionNoArg() //indent:18 exp:>=12
                     && fooooooooobooleanBooleanVeryLongName) {} //indent:21 exp:>=12
      } //indent:6 exp:6
    }; //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0

class SecondClassLongNam7 { //indent:0 exp:0

  public SecondClassLongNam7(String string) { //indent:2 exp:2

  } //indent:2 exp:2

  String getString(InputIndentationCorrectIfAndParameter instance, int integer) { //indent:2 exp:2
    return "String"; //indent:4 exp:4
  } //indent:2 exp:2

  int getInteger(InputIndentationCorrectIfAndParameter instance, String string) { //indent:2 exp:2
    return -1;   //indent:4 exp:4
  } //indent:2 exp:2

  boolean gB(InputIndentationCorrectIfAndParameter instance,boolean flag){ //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  SecondClassLongNam7 getInstance() { //indent:2 exp:2
    return new SecondClassLongNam7("VeryLoooooooooo" //indent:4 exp:4
        + "oongString"); //indent:8 exp:8
  } //indent:2 exp:2
} //indent:0 exp:0
