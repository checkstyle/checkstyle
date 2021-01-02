package com.google.checkstyle.test.chapter4formatting.rule4841indentation; //indent:0 exp:0

class InputIndentationCorrectIfAndParameter { //indent:0 exp:0

  String getString(int someInt, String someString) { //indent:2 exp:2
    return "String"; //indent:4 exp:4
  } //indent:2 exp:2

  void fooMethodWithIf() { //indent:2 exp:2

    if (conditionFirst("Loooooooooooooooooong", new //indent:4 exp:4
          SecondClassLongNam6("Loooooooooooooooooog"). //indent:10 exp:10
          getInteger(new FooIfClass(), "Loooooooooooooooooog"), //indent:10 exp:10
          new InnerClassFoo())) {} //indent:10 exp:10

    if (conditionSecond(10000000000.0, new //indent:4 exp:4
          SecondClassLongNam6("Looooooooooooo" //indent:10 exp:10
          + "oooooooooooong").getString(new FooIfClass(), //indent:10 exp:10
            new SecondClassLongNam6("loooooooooong"). //indent:12 exp:12,14
            getInteger(new FooIfClass(), "looooooooooooong")), "looooooooooong") //indent:12 exp:12
          || conditionThird(2048) || conditionFourth(new //indent:10 exp:10
          SecondClassLongNam6("Looooooooooooooo" //indent:10 exp:10
          + "ooooooooooooong").getBoolean(new FooIfClass(), false)) || //indent:10 exp:10
          conditionFifth(true, new SecondClassLongNam6(getString(2048, "Looo" //indent:10 exp:10
          + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:10 exp:10
            new FooIfClass(), true)) || conditionSixth(false, new //indent:12 exp:12,14
            SecondClassLongNam6(getString(100000, "Loooooong" //indent:12 exp:12
            + "Fooooooo><"))) || conditionNoArg() //indent:12 exp:12
          || conditionNoArg() || //indent:10 exp:10
          conditionNoArg() || conditionNoArg()) {} //indent:10 exp:10
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
      SecondClassLongNam6 instance) { //indent:6 exp:6
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  private boolean conditionNoArg() { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  class InnerClassFoo { //indent:2 exp:2

    void fooMethodWithIf() { //indent:4 exp:4
      if (conditionFirst("Loooooooooooooooooong", new //indent:6 exp:6
            SecondClassLongNam6("Loooooooooooooooooog"). //indent:12 exp:12
              getInteger(new FooIfClass(), "Loooooooooooooooooog"), //indent:14 exp:>=12
              new InnerClassFoo())) {} //indent:14 exp:>=12

      if (conditionSecond(10000000000.0, new //indent:6 exp:6
            SecondClassLongNam6("Looooooooooooo" //indent:12 exp:12
            + "oooooooooooong").getString(new FooIfClass(), //indent:12 exp:12
              new SecondClassLongNam6("loooooooooong"). //indent:14 exp:14
            getInteger(new FooIfClass(), "looooooooooooong")), "looooooooooong") //indent:12 exp:12
            || conditionThird(2048) || conditionFourth(new //indent:12 exp:12
            SecondClassLongNam6("Looooooooooooooo" //indent:12 exp:12
              + "ooooooooooooong").getBoolean(new FooIfClass(), false)) || //indent:14 exp:>=12
              conditionFifth(true, new SecondClassLongNam6(getString(2048, "Loo" //indent:14 exp:14
              + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:14 exp:14
                new FooIfClass(), true)) || conditionSixth(false, new //indent:16 exp:16,18
                SecondClassLongNam6(getString(100000, "Loooooong" //indent:16 exp:16
                + "Fooooooo><"))) || conditionNoArg() //indent:16 exp:16
                || conditionNoArg() || //indent:16 exp:16
                conditionNoArg() || conditionNoArg()) {} //indent:16 exp:16
    } //indent:4 exp:4

    FooIfClass anonymousClass = new FooIfClass() { //indent:4 exp:4

      void fooMethodWithIf(String stringStringStringStringLooooongString, //indent:6 exp:6
          int intIntIntVeryLongNameForIntVariable, boolean //indent:10 exp:10
              fooooooooobooleanBooleanVeryLongName) { //indent:14 exp:>=10

        if (conditionFirst("Loooooooooooooooooong", new //indent:8 exp:8
              SecondClassLongNam6("Loooooooooooooooooog"). //indent:14 exp:14
                getInteger(new FooIfClass(), "Loooooooooooooooooog"), //indent:16 exp:>=14
                   new InnerClassFoo())) {} //indent:19 exp:>=14

        if (conditionSecond(10000000000.0, new //indent:8 exp:8
              SecondClassLongNam6("Looooooooooooo" //indent:14 exp:14
              + "oooooooooooong").getString(new FooIfClass(), //indent:14 exp:14
                new SecondClassLongNam6("loooooooooong"). //indent:16 exp:16,18
              getInteger(new FooIfClass(), "loooooooooooong")), "loooooooooong") //indent:14 exp:14
              || conditionThird(2048) || conditionFourth(new //indent:14 exp:14
              SecondClassLongNam6("Looooooooooooooo" //indent:14 exp:14
                + "ooooooooooooong").getBoolean(new FooIfClass(), false)) || //indent:16 exp:>=14
                conditionFifth(true, new SecondClassLongNam6(getString(256, "" //indent:16 exp:>=14
                + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:16 exp:16
                  new FooIfClass(), true)) || conditionSixth(false, new //indent:18 exp:>=14
                  SecondClassLongNam6(getString(100000, "Loooooong" //indent:18 exp:18
                  + "Fooooooo><"))) || conditionNoArg() //indent:18 exp:18
                  || conditionNoArg() || //indent:18 exp:>=14
                    conditionNoArg() || conditionNoArg() //indent:20 exp:>=14
                       && fooooooooobooleanBooleanVeryLongName) {} //indent:23 exp:>=14
      } //indent:6 exp:6
    }; //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0

class SecondClassLongNam6 { //indent:0 exp:0

  public SecondClassLongNam6(String string) { //indent:2 exp:2

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

  SecondClassLongNam6 getInstance() { //indent:2 exp:2
    return new SecondClassLongNam6("VeryLoooooooooo" //indent:4 exp:4
        + "oongString"); //indent:8 exp:8
  } //indent:2 exp:2
} //indent:0 exp:0
class FooIfClass {} //indent:0 exp:0
