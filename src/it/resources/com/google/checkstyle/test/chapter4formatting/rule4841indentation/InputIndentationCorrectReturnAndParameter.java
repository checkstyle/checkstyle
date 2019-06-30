package com.google.checkstyle.test.chapter4formatting.rule4841indentation; //indent:0 exp:0

class InputIndentationCorrectReturnAndParameter { //indent:0 exp:0

  String getString(int someInt, String someString) { //indent:2 exp:2
    return "String"; //indent:4 exp:4
  } //indent:2 exp:2

  boolean fooMethodWithIf() { //indent:2 exp:2

    return conditionSecond(10000000000.0, new //indent:4 exp:4
        SecondClassLongName("Looooooooooooo" //indent:8 exp:8
        + "oooooooooooong").getString(new FooReturnClass(), //indent:8 exp:8
        new SecondClassLongName("loooooooooong"). //indent:8 exp:8
        getInteger(new FooReturnClass(), "loooooooooooooong")), "loooooooooooong") //indent:8 exp:8
        || conditionThird(2048) || conditionFourth(new //indent:8 exp:8
        SecondClassLongName("Looooooooooooooo" //indent:8 exp:8
        + "ooooooooooooong").getBoolean(new FooReturnClass(), false)) || //indent:8 exp:8
        conditionFifth(true, new SecondClassLongName(getString(2048, "Looo" //indent:8 exp:8
        + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:8 exp:8
        new FooReturnClass(), true)) || conditionSixth(false, new //indent:8 exp:8
        SecondClassLongName(getString(100000, "Loooooong" //indent:8 exp:8
        + "Fooooooo><"))) || conditionNoArg() //indent:8 exp:8
        || conditionNoArg() || //indent:8 exp:8
        conditionNoArg() || conditionNoArg();//indent:8 exp:8
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
      SecondClassLongName instance) { //indent:6 exp:6
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  private boolean conditionNoArg() { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  class InnerClassFoo { //indent:2 exp:2

    boolean fooMethodWithIf() { //indent:4 exp:4
      return conditionFirst("Loooooooooooooooooong", new //indent:6 exp:6
          SecondClassLongName("Loooooooooooooooooog"). //indent:10 exp:10
              getInteger(new FooReturnClass(), "Loooooooooooooooooog"), //indent:14 exp:>=10
              new InnerClassFoo()); //indent:14 exp:>=10
    } //indent:4 exp:4

    boolean fooReturn() { //indent:4 exp:4
      return conditionSecond(10000000000.0, new //indent:6 exp:6
          SecondClassLongName("Looooooooooooo" //indent:10 exp:10
          + "oooooooooooong").getString(new FooReturnClass(), //indent:10 exp:10
          new SecondClassLongName("loooooooooong"). //indent:10 exp:10
          getInteger(new FooReturnClass(), "looooooooooong")), "loooooooooooong") //indent:10 exp:10
          || conditionThird(2048) || conditionFourth(new //indent:10 exp:10
          SecondClassLongName("Looooooooooooooo" //indent:10 exp:10
            + "ooooooooooooong").getBoolean(new FooReturnClass(), false)) || //indent:12 exp:>=10
            conditionFifth(true, new SecondClassLongName(getString(2048, "Looo" //indent:12 exp:>=10
            + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:12 exp:>=10
            new FooReturnClass(), true)) || conditionSixth(false, new //indent:12 exp:>=10
              SecondClassLongName(getString(100000, "Loooooong" //indent:14 exp:>=10
              + "Fooooooo><"))) || conditionNoArg() //indent:14 exp:>=10
              || conditionNoArg() || //indent:14 exp:>=10
              conditionNoArg() || conditionNoArg(); //indent:14 exp:>=10
    } //indent:4 exp:4

    FooReturnClass anonymousClass = new FooReturnClass() { //indent:4 exp:4

      boolean fooMethodWithIf(String stringStringStringStringLooooongString, //indent:6 exp:6
          int intIntIntVeryLongNameForIntVariable, boolean //indent:10 exp:10
              fooooooooobooleanBooleanVeryLongName) { //indent:14 exp:>=10

        return conditionSecond(10000000000.0, new //indent:8 exp:8
            SecondClassLongName("Looooooooooooo" //indent:12 exp:12
            + "oooooooooooong").getString(new FooReturnClass(), //indent:12 exp:12
            new SecondClassLongName("loooooooooong"). //indent:12 exp:12
            getInteger(new FooReturnClass(), "looooooooong")), "loooooooooooong") //indent:12 exp:12
            || conditionThird(2048) || conditionFourth(new //indent:12 exp:12
            SecondClassLongName("Looooooooooooooo" //indent:12 exp:12
              + "ooooooooooooong").getBoolean(new FooReturnClass(), false)) || //indent:14 exp:>=12
              conditionFifth(true, new SecondClassLongName(getString(2048, "Lo" //indent:14 exp:>=12
              + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:14 exp:>=12
              new FooReturnClass(), true)) || conditionSixth(false, new //indent:14 exp:>=12
                SecondClassLongName(getString(100000, "Loooooong" //indent:16 exp:>=12
                + "Fooooooo><"))) || conditionNoArg() //indent:16 exp:>=12
                || conditionNoArg() || //indent:16 exp:>=12
                  conditionNoArg() || conditionNoArg() //indent:18 exp:>=12
                     && fooooooooobooleanBooleanVeryLongName; //indent:21 exp:>=12
      } //indent:6 exp:6

      boolean fooReturn() { //indent:6 exp:6
        return conditionFirst("Loooooooooooooooooong", new //indent:8 exp:8
            SecondClassLongName("Loooooooooooooooooog"). //indent:12 exp:12
                getInteger(new FooReturnClass(), "Loooooooooooooooooog"), //indent:16 exp:>=12
                   new InnerClassFoo()); //indent:19 exp:>=12
      } //indent:6 exp:6
    }; //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0

class SecondClassLongName { //indent:0 exp:0

  public SecondClassLongName(String string) { //indent:2 exp:2

  } //indent:2 exp:2

  String getString(FooReturnClass instance, int integer) { //indent:2 exp:2
    return "String"; //indent:4 exp:4
  } //indent:2 exp:2

  int getInteger(FooReturnClass instance, String string) { //indent:2 exp:2
    return -1;   //indent:4 exp:4
  } //indent:2 exp:2

  boolean getBoolean(FooReturnClass instance, boolean flag) { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  SecondClassLongName getInstance() { //indent:2 exp:2
    return new SecondClassLongName("VeryLoooooooooo" //indent:4 exp:4
        + "oongString"); //indent:8 exp:8
  } //indent:2 exp:2
} //indent:0 exp:0
class FooReturnClass {} //indent:0 exp:0
