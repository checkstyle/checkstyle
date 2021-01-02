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
            SecondClassLongNam7("Loooooooooooooooooog"). //indent:12 exp:12
            getInteger(new InputIndentationCorrectIfAndParameter(), "Looooog"), //indent:12 exp:12
            new InnerClassFoo())) {} //indent:12 exp:12

    InputIndentationCorrectIfAndParameter param = //indent:4 exp:4
        new InputIndentationCorrectIfAndParameter(); //indent:8 exp:8
    if (conditionSecond(10000000000.0, new //indent:4 exp:4
            SecondClassLongNam7("Looooooooooooo" //indent:12 exp:12
            + "oooooong").getString(new InputIndentationCorrectIfAndParameter(), //indent:12 exp:12
                new SecondClassLongNam7("loooooooooong"). //indent:16 exp:14,16
                getInteger(param, "long")), "loong") //indent:16 exp:16
            || conditionThird(2048) || conditionFourth(new //indent:12 exp:12
            SecondClassLongNam7("Looooooooooooooo" //indent:12 exp:12
            + "oo").gB(new InputIndentationCorrectIfAndParameter(), false)) || //indent:12 exp:12
            conditionFifth(true, new SecondClassLongNam7(getString(2048, "Looo" //indent:12 exp:12
            + "ooooooooooooooooooooooooooooooooooooooooooong")).gB( //indent:12 exp:12
                new InputIndentationCorrectIfAndParameter(), true)) //indent:16 exp:14,16
                || co(false, new //indent:16 exp:16
                SecondClassLongNam7(getString(100000, "Loooooong" //indent:16 exp:16
                + "Fooooooo><"))) || conditionNoArg() //indent:16 exp:16
                || conditionNoArg() || //indent:16 exp:16
                conditionNoArg() || conditionNoArg()) {} //indent:16 exp:16
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
              SecondClassLongNam7("Loooooooooooooooooog"). //indent:14 exp:14
              getInteger(new InputIndentationCorrectIfAndParameter(), "Log"), //indent:14 exp:14
              new InnerClassFoo())) {} //indent:14 exp:14

      InputIndentationCorrectIfAndParameter param = //indent:6 exp:6
	      new InputIndentationCorrectIfAndParameter(); //indent:10 exp:10
      if (conditionSecond(10000000000.0, new //indent:6 exp:6
              SecondClassLongNam7("Looooooooooooo" //indent:14 exp:14
              + "oooong").getString(new InputIndentationCorrectIfAndParameter(), //indent:14 exp:14
                  new SecondClassLongNam7("loooooooooong"). //indent:18 exp:16,18
                  getInteger(param, "long")), "l") //indent:18 exp:18
              || conditionThird(2048) || conditionFourth(new //indent:14 exp:14
              SecondClassLongNam7("Looooooooooooooo" //indent:14 exp:14
                + "og").gB(param, false)) || //indent:16 exp:>=14
                conditionFifth(true, new SecondClassLongNam7(getString(256, "" //indent:16 exp:>=14
                + "ooooooooooooooooooooooooooooooooooooooooooong")).gB( //indent:16 exp:>=14
                  new InputIndentationCorrectIfAndParameter(), true)) //indent:18 exp:18,20
                  ||co(false,new //indent:18 exp:18
                  SecondClassLongNam7(getString(100000, "Loooooong" //indent:18 exp:18
                  + "Fooooooo><"))) || conditionNoArg() //indent:18 exp:18
                  || conditionNoArg() || //indent:18 exp:18
                  conditionNoArg() || conditionNoArg()) {} //indent:18 exp:18
    } //indent:4 exp:4

    Object ann = new Object() { //indent:4 exp:4

      void fooMethodWithIf(String stringStringStringStringLooooongString, //indent:6 exp:6
          int intIntIntVeryLongNameForIntVariable, boolean //indent:10 exp:10
              fooooooooobooleanBooleanVeryLongName) { //indent:14 exp:>=10

        if (conditionFirst("Loooooooooooooooooong", new //indent:8 exp:8
                SecondClassLongNam7("Loooooooooooooooooog"). //indent:16 exp:16
                getInteger(new InputIndentationCorrectIfAndParameter(), "Lg"), //indent:16 exp:16
                   new InnerClassFoo())) {} //indent:19 exp:>=16

        InputIndentationCorrectIfAndParameter param = //indent:8 exp:8
            new InputIndentationCorrectIfAndParameter(); //indent:12 exp:12
        if (conditionSecond(10000000000.0, new //indent:8 exp:8
                SecondClassLongNam7("Looooooooooooo" //indent:16 exp:16
                + "oong").getString(new InputIndentationCorrectIfAndParameter(), //indent:16 exp:16
                    new SecondClassLongNam7("loooooooooong"). //indent:20 exp:18,20
                    getInteger(param, "lg")), "l") //indent:20 exp:20
                || conditionThird(2048) || conditionFourth(new //indent:16 exp:16
                SecondClassLongNam7("Looooooooooooooo" //indent:16 exp:16
                  + "o").gB(param, false))|| //indent:18 exp:>=16
                  conditionFifth(true, new SecondClassLongNam7(getString(8, "" //indent:18 exp:>=16
                  + "ooooooooooooooooooooooooooooooooooooooooooong")).gB( //indent:18 exp:>=16
                    new InputIndentationCorrectIfAndParameter(),true))//indent:20 exp:20,22
                    || co(false, new //indent:20 exp:20
                    SecondClassLongNam7(getString(100000, "Loooooong" //indent:20 exp:20
                    + "Fooooooo><"))) || conditionNoArg() //indent:20 exp:20
                    || conditionNoArg() || //indent:20 exp:20
                      conditionNoArg() || conditionNoArg() //indent:22 exp:>=20
                         && fooooooooobooleanBooleanVeryLongName) {} //indent:25 exp:>=20
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
