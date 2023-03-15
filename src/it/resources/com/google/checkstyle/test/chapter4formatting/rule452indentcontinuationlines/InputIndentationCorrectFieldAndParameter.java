package com.google.checkstyle.test.chapter4formatting.rule452indentcontinuationlines; //indent:0 exp:0

class InputIndentationCorrectFieldAndParameter { //indent:0 exp:0

  boolean flag = conditionFirst("Loooooooooooooooooong", new //indent:2 exp:2
      SecondFieldWithName("Loooooooooooooooooog"). //indent:6 exp:6
      getInteger(new FooFieldClass(), "Loooooooooooooooooog"), //indent:6 exp:6
      new InnerClassFoo()); //indent:6 exp:6

  boolean secondFlag = conditionSecond(10000000000.0, new //indent:2 exp:2
      SecondFieldWithName("Looooooooooooo" //indent:6 exp:6
      + "oooooooooooong").getString(new FooFieldClass(), //indent:6 exp:6
      new SecondFieldWithName("loooooooooong"). //indent:6 exp:6
      getInteger(new FooFieldClass(), "loooooooooooooong")), "loooooooooooong") //indent:6 exp:6
      || conditionThird(2048) || conditionFourth(new //indent:6 exp:6
      SecondFieldWithName("Looooooooooooooo" //indent:6 exp:6
      + "ooooooooooooong").getBoolean(new FooFieldClass(), false)) || //indent:6 exp:6
      conditionFifth(true, new SecondFieldWithName(getString(2048, "Looo" //indent:6 exp:6
      + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:6 exp:6
      new FooFieldClass(), true)) || conditionSixth(false, new //indent:6 exp:6
      SecondFieldWithName(getString(100000, "Loooooong" //indent:6 exp:6
      + "Fooooooo><"))) || conditionNoArg() //indent:6 exp:6
      || conditionNoArg() || //indent:6 exp:6
      conditionNoArg() || conditionNoArg(); //indent:6 exp:6

  String getString(int someInt, String someString) { //indent:2 exp:2
    return "String"; //indent:4 exp:4
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
      SecondFieldWithName instance) { //indent:6 exp:6
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  private boolean conditionNoArg() { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  class InnerClassFoo { //indent:2 exp:2

    boolean flag = conditionFirst("Loooooooooooooooooong", new //indent:4 exp:4
        SecondFieldWithName("Loooooooooooooooooog"). //indent:8 exp:8
            getInteger(new FooFieldClass(), "Loooooooooooooooooog"), //indent:12 exp:>=8
             new InnerClassFoo()); //indent:13 exp:>=8

    boolean secondFlag = conditionSecond(10000000000.0, new //indent:4 exp:4
        SecondFieldWithName("Looooooooooooo" //indent:8 exp:8
           + "oooooooooooong").getString(new FooFieldClass(), //indent:11 exp:>=8
          new SecondFieldWithName("loooooooooong"). //indent:10 exp:>=8
            getInteger(new FooFieldClass(), "looooooooong")), "loooooooooooong") //indent:12 exp:>=8
             || conditionThird(2048) || conditionFourth(new //indent:13 exp:>=8
                SecondFieldWithName("Looooooooooooooo" //indent:16 exp:>=8
            + "ooooooooooooong").getBoolean(new FooFieldClass(), false)) || //indent:12 exp:>=8
           conditionFifth(true, new SecondFieldWithName(getString(2048, "Looo" //indent:11 exp:>=8
               + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:15 exp:>=8
            new FooFieldClass(), true)) || conditionSixth(false, new //indent:12 exp:>=8
            SecondFieldWithName(getString(100000, "Loooooong" //indent:12 exp:>=8
                + "Fooooooo><"))) || conditionNoArg() //indent:16 exp:>=8
          || conditionNoArg() || //indent:10 exp:>=8
          conditionNoArg() || conditionNoArg(); //indent:10 exp:>=8

    FooFieldClass anonymousClass = new FooFieldClass() { //indent:4 exp:4
      boolean secondFlag = conditionSecond(10000000000.0, new //indent:6 exp:6
          SecondFieldWithName("Looooooooooooo" //indent:10 exp:10
            + "oooooooooooong").getString(new FooFieldClass(), //indent:12 exp:>=10
               new SecondFieldWithName("loooooooooong"). //indent:15 exp:>=10
             getInteger(new FooFieldClass(), "looooooong")), "loooooooooooong") //indent:13 exp:>=10
              || conditionThird(2048) || conditionFourth(new //indent:14 exp:>=10
                 SecondFieldWithName("Looooooooooooooo" //indent:17 exp:>=10
             + "ooooooooooooong").getBoolean(new FooFieldClass(), false)) || //indent:13 exp:>=10
            conditionFifth(true, new SecondFieldWithName(getString(2048, "Looo" //indent:12 exp:>=10
                + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:16 exp:>=10
             new FooFieldClass(), true)) || conditionSixth(false, new //indent:13 exp:>=8
             SecondFieldWithName(getString(100000, "Loooooong" //indent:13 exp:>=10
                 + "Fooooooo><"))) || conditionNoArg() //indent:17 exp:>=10
           || conditionNoArg() || //indent:11 exp:>=10
           conditionNoArg() || conditionNoArg(); //indent:11 exp:>=10
    }; //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0

class SecondFieldWithName { //indent:0 exp:0

  public SecondFieldWithName(String string) { //indent:2 exp:2

  } //indent:2 exp:2

  String getString(FooFieldClass instance, int integer) { //indent:2 exp:2
    return "String"; //indent:4 exp:4
  } //indent:2 exp:2

  int getInteger(FooFieldClass instance, String string) { //indent:2 exp:2
    return -1;   //indent:4 exp:4
  } //indent:2 exp:2

  boolean getBoolean(FooFieldClass instance, boolean flag) { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  SecondFieldWithName getInstance() { //indent:2 exp:2
    return new SecondFieldWithName("VeryLoooooooooo" //indent:4 exp:4
        + "oongString"); //indent:8 exp:8
  } //indent:2 exp:2
} //indent:0 exp:0
class FooFieldClass {} //indent:0 exp:0
