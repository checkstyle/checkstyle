package com.puppycrawl.tools.checkstyle.indentation; //indent:0 exp:0

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
class FooIfClass { //indent:0 exp:0

  String getString(int someInt, String someString) { //indent:2 exp:2
    return "String"; //indent:4 exp:4
  } //indent:2 exp:2

  void fooMethodWithIf() { //indent:2 exp:2

    if (conditionFirst("Loooooooooooooooooong", new //indent:4 exp:4
        SecondClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). //indent:8 exp:8
        getInteger(new FooIfClass(), "Loooooooooooooooooog"), //indent:8 exp:8
        new InnerClassFoo())) {} //indent:8 exp:8

    if (conditionSecond(10000000000.0, new //indent:4 exp:4
        SecondClassWithVeryVeryVeryLongName("Looooooooooooo" //indent:8 exp:8
        + "oooooooooooong").getString(new FooIfClass(), //indent:8 exp:8
        new SecondClassWithVeryVeryVeryLongName("loooooooooong"). //indent:8 exp:8
        getInteger(new FooIfClass(), "loooooooooooooong")), "loooooooooooong") //indent:8 exp:8
        || conditionThird(2048) || conditionFourth(new //indent:8 exp:8
        SecondClassWithVeryVeryVeryLongName("Looooooooooooooo" //indent:8 exp:8
        + "ooooooooooooong").getBoolean(new FooIfClass(), false)) || //indent:8 exp:8
        conditionFifth(true, new SecondClassWithVeryVeryVeryLongName(getString(2048, "Looo" //indent:8 exp:8
        + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:8 exp:8
        new FooIfClass(), true)) || conditionSixth(false, new //indent:8 exp:8
        SecondClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" //indent:8 exp:8
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

  private boolean conditionSixth(boolean flag, //indent:2 exp:2
      SecondClassWithVeryVeryVeryLongName instance) { //indent:6 exp:6
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  private boolean conditionNoArg() { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  class InnerClassFoo { //indent:2 exp:2

    void fooMethodWithIf() { //indent:4 exp:4
      if (conditionFirst("Loooooooooooooooooong", new //indent:6 exp:6
          SecondClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). //indent:10 exp:10
              getInteger(new FooIfClass(), "Loooooooooooooooooog"), //indent:14 exp:>=10
              new InnerClassFoo())) {} //indent:14 exp:>=10

      if (conditionSecond(10000000000.0, new //indent:6 exp:6
          SecondClassWithVeryVeryVeryLongName("Looooooooooooo" //indent:10 exp:10
          + "oooooooooooong").getString(new FooIfClass(), //indent:10 exp:10
          new SecondClassWithVeryVeryVeryLongName("loooooooooong"). //indent:10 exp:10
          getInteger(new FooIfClass(), "loooooooooooooong")), "loooooooooooong") //indent:10 exp:10
          || conditionThird(2048) || conditionFourth(new //indent:10 exp:10
          SecondClassWithVeryVeryVeryLongName("Looooooooooooooo" //indent:10 exp:10
            + "ooooooooooooong").getBoolean(new FooIfClass(), false)) || //indent:12 exp:>=10
            conditionFifth(true, new SecondClassWithVeryVeryVeryLongName(getString(2048, "Looo" //indent:12 exp:>=10
            + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:12 exp:>=10
            new FooIfClass(), true)) || conditionSixth(false, new //indent:12 exp:>=10
              SecondClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" //indent:14 exp:>=10
              + "Fooooooo><"))) || conditionNoArg() //indent:14 exp:>=10
              || conditionNoArg() || //indent:14 exp:>=10
              conditionNoArg() || conditionNoArg()) {} //indent:14 exp:>=10
    } //indent:4 exp:4

    FooIfClass anonymousClass = new FooIfClass() { //indent:4 exp:4

      void fooMethodWithIf(String stringStringStringStringLooooongString, //indent:6 exp:6
          int intIntIntVeryLongNameForIntVariable, boolean //indent:10 exp:10
              fooooooooobooleanBooleanVeryLongName) { //indent:14 exp:>=10

        if (conditionFirst("Loooooooooooooooooong", new //indent:8 exp:8
            SecondClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). //indent:12 exp:12
                getInteger(new FooIfClass(), "Loooooooooooooooooog"), //indent:16 exp:>=12
                   new InnerClassFoo())) {} //indent:19 exp:>=12

        if (conditionSecond(10000000000.0, new //indent:8 exp:8
            SecondClassWithVeryVeryVeryLongName("Looooooooooooo" //indent:12 exp:12
            + "oooooooooooong").getString(new FooIfClass(), //indent:12 exp:12
            new SecondClassWithVeryVeryVeryLongName("loooooooooong"). //indent:12 exp:12
            getInteger(new FooIfClass(), "loooooooooooooong")), "loooooooooooong") //indent:12 exp:12
            || conditionThird(2048) || conditionFourth(new //indent:12 exp:12
            SecondClassWithVeryVeryVeryLongName("Looooooooooooooo" //indent:12 exp:12
              + "ooooooooooooong").getBoolean(new FooIfClass(), false)) || //indent:14 exp:>=12
              conditionFifth(true, new SecondClassWithVeryVeryVeryLongName(getString(2048, "Looo" //indent:14 exp:>=12
              + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:14 exp:>=12
              new FooIfClass(), true)) || conditionSixth(false, new //indent:14 exp:>=12
                SecondClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" //indent:16 exp:>=12
                + "Fooooooo><"))) || conditionNoArg() //indent:16 exp:>=12
                || conditionNoArg() || //indent:16 exp:>=12
                  conditionNoArg() || conditionNoArg() //indent:18 exp:>=12
                     && fooooooooobooleanBooleanVeryLongName) {} //indent:21 exp:>=12
      } //indent:6 exp:6
    }; //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0

class SecondClassWithVeryVeryVeryLongName { //indent:0 exp:0

  public SecondClassWithVeryVeryVeryLongName(String string) { //indent:2 exp:2

  } //indent:2 exp:2

  String getString(FooIfClass instance, int integer) { //indent:2 exp:2
    return "String"; //indent:4 exp:4
  } //indent:2 exp:2

  int getInteger(FooIfClass instance, String string) { //indent:2 exp:2
    return -1;   //indent:4 exp:4
  } //indent:2 exp:2

  boolean getBoolean(FooIfClass instance, boolean flag) { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  SecondClassWithVeryVeryVeryLongName getInstanse() { //indent:2 exp:2
    return new SecondClassWithVeryVeryVeryLongName("VeryLoooooooooo" //indent:4 exp:4
        + "oongString"); //indent:8 exp:8
  } //indent:2 exp:2
} //indent:0 exp:0
