package com.google.checkstyle.test.chapter4formatting.rule452indentcontinuationlines; //indent:0 exp:0

class FooWhileClass { //indent:0 exp:0

  String getString(int someInt, String someString) { //indent:2 exp:2
    return "String"; //indent:4 exp:4
  } //indent:2 exp:2

  void fooMethodWithIf() { //indent:2 exp:2

    while (conditionFirst("Loooooooooooooooooong", new //indent:4 exp:4
        SecondWhileClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). //indent:8 exp:8
           getInteger(new FooWhileClass(), "Loooooooooooooooooog"), //indent:11 exp:>=8
          new InnerClassFoo())) {} //indent:10 exp:>=8

    do { //indent:4 exp:4

    } while (conditionFirst("Loooooooooooooooooong", new //indent:4 exp:4
         SecondWhileClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). //indent:9 exp:>=8
               getInteger(new FooWhileClass(), "Loooooooooooooooooog"), //indent:15 exp:>=8
         new InnerClassFoo())); //indent:9 exp:>=8

    while (conditionSecond(10000000000.0, new //indent:4 exp:4
         SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooo" //indent:9 exp:>=8
        + "oooooooooooong").getString(new FooWhileClass(), //indent:8 exp:8
           new SecondWhileClassWithVeryVeryVeryLongName("loooooooooong"). //indent:11 exp:>=8
        getInteger(new FooWhileClass(), "loooooooooooooong")), "loooooooooooong") //indent:8 exp:8
              || conditionThird(2048) || conditionFourth(new //indent:14 exp:>=8
              SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooooo" //indent:14 exp:>=8
        + "ooooooooooooong").getBoolean(new FooWhileClass(), false)) || //indent:8 exp:8
            conditionFifth(true, new SecondWhileClassWithVeryVeryVeryLongName( //indent:12 exp:>=8
            getString(2048, "Looo" //indent:12 exp:>=8
        + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:8 exp:8
        new FooWhileClass(), true)) || conditionSixth(false, new //indent:8 exp:8
           SecondWhileClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" //indent:11 exp:>=8
        + "Fooooooo><"))) || conditionNoArg() //indent:8 exp:8
          || conditionNoArg() || //indent:10 exp:>=8
          conditionNoArg() || conditionNoArg()) {} //indent:10 exp:10

    do { //indent:4 exp:4

    } while (conditionSecond(10000000000.0, new //indent:4 exp:4
          SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooo" //indent:10 exp:>=8
          + "oooooooooooong").getString(new FooWhileClass(), //indent:10 exp:>=8
        new SecondWhileClassWithVeryVeryVeryLongName("loooooooooong"). //indent:8 exp:8
             getInteger(new FooWhileClass(), "loooooooooooooong")), "loooooooooooong") //indent:13 exp:>=8
        || conditionThird(2048) || conditionFourth(new //indent:8 exp:8
           SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooooo" //indent:11 exp:>=8
        + "ooooooooooooong").getBoolean(new FooWhileClass(), false)) || //indent:8 exp:8
        conditionFifth(true, new SecondWhileClassWithVeryVeryVeryLongName(getString(2048, "Looo" //indent:8 exp:8
        + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:8 exp:8
        new FooWhileClass(), true)) || conditionSixth(false, new //indent:8 exp:8
           SecondWhileClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" //indent:11 exp:>=8
        + "Fooooooo><"))) || conditionNoArg() //indent:8 exp:8
             || conditionNoArg() || //indent:13 exp:>=8
        conditionNoArg() || conditionNoArg()); //indent:8 exp:8
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
      SecondWhileClassWithVeryVeryVeryLongName instance) { //indent:6 exp:6
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  private boolean conditionNoArg() { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  class InnerClassFoo { //indent:2 exp:2

    void fooMethodWithIf() { //indent:4 exp:4
      while (conditionFirst("Loooooooooooooooooong", new //indent:6 exp:6
          SecondWhileClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). //indent:10 exp:10
              getInteger(new FooWhileClass(), "Loooooooooooooooooog"), //indent:14 exp:>=10
              new InnerClassFoo())) {} //indent:14 exp:>=10

      do { //indent:6 exp:6
        /* Do somethig*/ //indent:8 exp:8
      } while (conditionFirst("Loooooooooooooooooong", new //indent:6 exp:6
          SecondWhileClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). //indent:10 exp:10
              getInteger(new FooWhileClass(), "Loooooooooooooooooog"), //indent:14 exp:>=10
              new InnerClassFoo())); //indent:14 exp:>=10

      while (conditionSecond(10000000000.0, new //indent:6 exp:6
          SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooo" //indent:10 exp:10
          + "oooooooooooong").getString(new FooWhileClass(), //indent:10 exp:10
          new SecondWhileClassWithVeryVeryVeryLongName("loooooooooong"). //indent:10 exp:10
          getInteger(new FooWhileClass(), "loooooooooooooong")), "loooooooooooong") //indent:10 exp:10
          || conditionThird(2048) || conditionFourth(new //indent:10 exp:10
          SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooooo" //indent:10 exp:10
            + "ooooooooooooong").getBoolean(new FooWhileClass(), false)) || //indent:12 exp:>=10
            conditionFifth(true, new SecondWhileClassWithVeryVeryVeryLongName(getString(2048, "Looo" //indent:12 exp:>=10
            + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:12 exp:>=10
            new FooWhileClass(), true)) || conditionSixth(false, new //indent:12 exp:>=10
              SecondWhileClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" //indent:14 exp:>=10
              + "Fooooooo><"))) || conditionNoArg() //indent:14 exp:>=10
              || conditionNoArg() || //indent:14 exp:>=10
              conditionNoArg() || conditionNoArg()) {} //indent:14 exp:>=10

      do { //indent:6 exp:6
    	/* Do smth*/ //indent:8 exp:8
      } while (conditionSecond(10000000000.0, new //indent:6 exp:6
          SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooo" //indent:10 exp:10
          + "oooooooooooong").getString(new FooWhileClass(), //indent:10 exp:10
          new SecondWhileClassWithVeryVeryVeryLongName("loooooooooong"). //indent:10 exp:10
          getInteger(new FooWhileClass(), "loooooooooooooong")), "loooooooooooong") //indent:10 exp:10
          || conditionThird(2048) || conditionFourth(new //indent:10 exp:10
          SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooooo" //indent:10 exp:10
            + "ooooooooooooong").getBoolean(new FooWhileClass(), false)) || //indent:12 exp:>=10
            conditionFifth(true, new SecondWhileClassWithVeryVeryVeryLongName(getString(2048, "Looo" //indent:12 exp:>=10
            + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:12 exp:>=10
            new FooWhileClass(), true)) || conditionSixth(false, new //indent:12 exp:>=10
              SecondWhileClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" //indent:14 exp:>=10
              + "Fooooooo><"))) || conditionNoArg() //indent:14 exp:>=10
              || conditionNoArg() || //indent:14 exp:>=10
              conditionNoArg() || conditionNoArg()); //indent:14 exp:>=10
    } //indent:4 exp:4

    FooWhileClass anonymousClass = new FooWhileClass() { //indent:4 exp:4

      void fooMethodWithIf(String stringStringStringStringLooooongString, //indent:6 exp:6
          int intIntIntVeryLongNameForIntVariable, boolean //indent:10 exp:10
              fooooooooobooleanBooleanVeryLongName) { //indent:14 exp:>=10

        while (conditionFirst("Loooooooooooooooooong", new //indent:8 exp:8
            SecondWhileClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). //indent:12 exp:12
                getInteger(new FooWhileClass(), "Loooooooooooooooooog"), //indent:16 exp:>=12
                   new InnerClassFoo())) {} //indent:19 exp:>=12

        do { //indent:8 exp:8
          /* Do smth*/ //indent:10 exp:10
        } while (conditionFirst("Loooooooooooooooooong", new //indent:8 exp:8
            SecondWhileClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). //indent:12 exp:12
                getInteger(new FooWhileClass(), "Loooooooooooooooooog"), //indent:16 exp:>=12
                   new InnerClassFoo())); //indent:19 exp:>=12

        while (conditionSecond(10000000000.0, new //indent:8 exp:8
            SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooo" //indent:12 exp:12
            + "oooooooooooong").getString(new FooWhileClass(), //indent:12 exp:12
            new SecondWhileClassWithVeryVeryVeryLongName("loooooooooong"). //indent:12 exp:12
            getInteger(new FooWhileClass(), "loooooooooooooong")), "loooooooooooong") //indent:12 exp:12
            || conditionThird(2048) || conditionFourth(new //indent:12 exp:12
            SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooooo" //indent:12 exp:12
              + "ooooooooooooong").getBoolean(new FooWhileClass(), false)) || //indent:14 exp:>=12
              conditionFifth(true, new SecondWhileClassWithVeryVeryVeryLongName(getString(2048, "Looo" //indent:14 exp:>=12
              + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:14 exp:>=12
              new FooWhileClass(), true)) || conditionSixth(false, new //indent:14 exp:>=12
                SecondWhileClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" //indent:16 exp:>=12
                + "Fooooooo><"))) || conditionNoArg() //indent:16 exp:>=12
                || conditionNoArg() || //indent:16 exp:>=12
                  conditionNoArg() || conditionNoArg() //indent:18 exp:>=12
                     && fooooooooobooleanBooleanVeryLongName) {} //indent:21 exp:>=12

        do { //indent:8 exp:8
          /* Do smth*/ //indent:10 exp:10
        } while (conditionSecond(10000000000.0, new //indent:8 exp:8
            SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooo" //indent:12 exp:12
            + "oooooooooooong").getString(new FooWhileClass(), //indent:12 exp:12
            new SecondWhileClassWithVeryVeryVeryLongName("loooooooooong"). //indent:12 exp:12
            getInteger(new FooWhileClass(), "loooooooooooooong")), "loooooooooooong") //indent:12 exp:12
            || conditionThird(2048) || conditionFourth(new //indent:12 exp:12
            SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooooo" //indent:12 exp:12
              + "ooooooooooooong").getBoolean(new FooWhileClass(), false)) || //indent:14 exp:>=12
              conditionFifth(true, new SecondWhileClassWithVeryVeryVeryLongName(getString(2048, "Looo" //indent:14 exp:>=12
              + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( //indent:14 exp:>=12
              new FooWhileClass(), true)) || conditionSixth(false, new //indent:14 exp:>=12
                SecondWhileClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" //indent:16 exp:>=12
                + "Fooooooo><"))) || conditionNoArg() //indent:16 exp:>=12
                || conditionNoArg() || //indent:16 exp:>=12
                  conditionNoArg() || conditionNoArg() //indent:18 exp:>=12
                     && fooooooooobooleanBooleanVeryLongName);//indent:21 exp:>=12
      } //indent:6 exp:6
    }; //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0

class SecondWhileClassWithVeryVeryVeryLongName { //indent:0 exp:0

  public SecondWhileClassWithVeryVeryVeryLongName(String string) { //indent:2 exp:2

  } //indent:2 exp:2

  String getString(FooWhileClass instance, int integer) { //indent:2 exp:2
    return "String"; //indent:4 exp:4
  } //indent:2 exp:2

  int getInteger(FooWhileClass instance, String string) { //indent:2 exp:2
    return -1;   //indent:4 exp:4
  } //indent:2 exp:2

  boolean getBoolean(FooWhileClass instance, boolean flag) { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  SecondWhileClassWithVeryVeryVeryLongName getInstanse() { //indent:2 exp:2
    return new SecondWhileClassWithVeryVeryVeryLongName("VeryLoooooooooo" //indent:4 exp:4
        + "oongString"); //indent:8 exp:8
  } //indent:2 exp:2
} //indent:0 exp:0
