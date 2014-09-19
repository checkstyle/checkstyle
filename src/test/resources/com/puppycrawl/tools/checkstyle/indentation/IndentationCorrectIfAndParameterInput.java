package com.google.checkstyle.test.chapter4formatting.rule4841indentation; // indent:0 ; exp:0; ok

class FooIfClass { // indent:0 ; exp:0; ok
    
  String getString(int someInt, String someString) { // indent:2 ; exp:2; ok
    return "String"; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok

  void fooMethodWithIf() { // indent:2 ; exp:2; ok

    if (conditionFirst("Loooooooooooooooooong", new // indent:4 ; exp:4; ok
        SecondClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). // indent:8 ; exp:8; ok
        getInteger(new FooIfClass(), "Loooooooooooooooooog"), // indent:8 ; exp:8; ok
        new InnerClassFoo())) {}
    
    if (conditionSecond(10000000000.0, new // indent:4 ; exp:4; ok
        SecondClassWithVeryVeryVeryLongName("Looooooooooooo" // indent:8 ; exp:8; ok
        + "oooooooooooong").getString(new FooIfClass(), // indent:8 ; exp:8; ok
        new SecondClassWithVeryVeryVeryLongName("loooooooooong"). // indent:8 ; exp:8; ok
        getInteger(new FooIfClass(), "loooooooooooooong")), "loooooooooooong") // indent:8 ; exp:8; ok
        || conditionThird(2048) || conditionFourth(new // indent:8 ; exp:8; ok
        SecondClassWithVeryVeryVeryLongName("Looooooooooooooo" // indent:8 ; exp:8; ok
        + "ooooooooooooong").getBoolean(new FooIfClass(), false)) || // indent:8 ; exp:8; ok
        conditionFifth(true, new SecondClassWithVeryVeryVeryLongName(getString(2048, "Looo" // indent:8 ; exp:8; ok
        + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( // indent:8 ; exp:8; ok
        new FooIfClass(), true)) || conditionSixth(false, new // indent:8 ; exp:8; ok
        SecondClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" // indent:8 ; exp:8; ok
        + "Fooooooo><"))) || conditionNoArg() // indent:8 ; exp:8; ok
        || conditionNoArg() || // indent:8 ; exp:8; ok
        conditionNoArg() || conditionNoArg()) {} // indent:8 ; exp:8; ok
  } // indent:2 ; exp:2; ok

  private boolean conditionFirst(String longString, int // indent:2 ; exp:2; ok
      integer, InnerClassFoo someInstance) { // indent:6 ; exp:6; ok
    return false; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  private boolean conditionSecond(double longLongLongDoubleValue, // indent:2 ; exp:2; ok
      String longLongLongString, String secondLongLongString) { // indent:6 ; exp:6; ok
    return false; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  private boolean conditionThird(long veryLongValue) { // indent:2 ; exp:2; ok
    return false; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok

  private boolean conditionFourth(boolean flag) { // indent:2 ; exp:2; ok
    return false; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  private boolean conditionFifth(boolean flag1, boolean flag2) { // indent:2 ; exp:2; ok
    return false; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  private boolean conditionSixth(boolean flag, // indent:2 ; exp:2; ok
      SecondClassWithVeryVeryVeryLongName instance) { // indent:6 ; exp:6; ok
    return false; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  private boolean conditionNoArg() { // indent:2 ; exp:2; ok
    return false; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  class InnerClassFoo { // indent:2 ; exp:2; ok

    void fooMethodWithIf() { // indent:4 ; exp:4; ok
      if (conditionFirst("Loooooooooooooooooong", new // indent:6 ; exp:6; ok
          SecondClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). // indent:10 ; exp:10; ok
              getInteger(new FooIfClass(), "Loooooooooooooooooog"), // indent:14 ; exp:>10; ok
              new InnerClassFoo())) {} // indent:14 ; exp:>10; ok

      if (conditionSecond(10000000000.0, new // indent:6 ; exp:6; ok
          SecondClassWithVeryVeryVeryLongName("Looooooooooooo" // indent:10 ; exp:10; ok
          + "oooooooooooong").getString(new FooIfClass(), // indent:10 ; exp:10; ok
          new SecondClassWithVeryVeryVeryLongName("loooooooooong"). // indent:10 ; exp:10; ok
          getInteger(new FooIfClass(), "loooooooooooooong")), "loooooooooooong") // indent:10 ; exp:10; ok
          || conditionThird(2048) || conditionFourth(new // indent:10 ; exp:10; ok
          SecondClassWithVeryVeryVeryLongName("Looooooooooooooo" // indent:10 ; exp:10; ok
            + "ooooooooooooong").getBoolean(new FooIfClass(), false)) || // indent:12 ; exp:>10; ok
            conditionFifth(true, new SecondClassWithVeryVeryVeryLongName(getString(2048, "Looo" // indent:12 ; exp:>10; ok
            + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( // indent:12 ; exp:>10; ok
            new FooIfClass(), true)) || conditionSixth(false, new // indent:12 ; exp:>10; ok
              SecondClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" // indent:14 ; exp:>10; ok
              + "Fooooooo><"))) || conditionNoArg() // indent:14 ; exp:>10; ok
              || conditionNoArg() || // indent:14 ; exp:>10; ok
              conditionNoArg() || conditionNoArg()) {} // indent:14 ; exp:>10; ok
    } // indent:4 ; exp:4; ok

    FooIfClass anonymousClass = new FooIfClass() { // indent:4 ; exp:4; ok

      void fooMethodWithIf(String stringStringStringStringLooooongString, // indent:6 ; exp:6; ok
          int intIntIntVeryLongNameForIntVariable, boolean // indent:10 ; exp:10; ok
              fooooooooobooleanBooleanVeryLongName) { // indent:14 ; exp:>10; ok

        if (conditionFirst("Loooooooooooooooooong", new // indent:8 ; exp:8; ok
            SecondClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). // indent:12 ; exp:12; ok
                getInteger(new FooIfClass(), "Loooooooooooooooooog"), // indent:16 ; exp:>12; ok
                   new InnerClassFoo())) {} // indent:19 ; exp:>12; ok

        if (conditionSecond(10000000000.0, new // indent:8 ; exp:8; ok
            SecondClassWithVeryVeryVeryLongName("Looooooooooooo" // indent:12 ; exp:12; ok
            + "oooooooooooong").getString(new FooIfClass(), // indent:12 ; exp:12; ok
            new SecondClassWithVeryVeryVeryLongName("loooooooooong"). // indent:12 ; exp:12; ok
            getInteger(new FooIfClass(), "loooooooooooooong")), "loooooooooooong") // indent:12 ; exp:12; ok
            || conditionThird(2048) || conditionFourth(new // indent:12 ; exp:12; ok
            SecondClassWithVeryVeryVeryLongName("Looooooooooooooo" // indent:12 ; exp:12; ok
              + "ooooooooooooong").getBoolean(new FooIfClass(), false)) || // indent:14 ; exp:>12; ok
              conditionFifth(true, new SecondClassWithVeryVeryVeryLongName(getString(2048, "Looo" // indent:14 ; exp:>12; ok
              + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( // indent:14 ; exp:>12; ok
              new FooIfClass(), true)) || conditionSixth(false, new // indent:14 ; exp:>12; ok
                SecondClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" // indent:16 ; exp:>12; ok
                + "Fooooooo><"))) || conditionNoArg() // indent:16 ; exp:>12; ok
                || conditionNoArg() || // indent:16 ; exp:>12; ok
                  conditionNoArg() || conditionNoArg() // indent:18 ; exp:>12; ok
                     && fooooooooobooleanBooleanVeryLongName) {} // indent:21 ; exp:>12; ok
      } // indent:6 ; exp:6; ok
    }; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
}

class SecondClassWithVeryVeryVeryLongName { // indent:0 ; exp:0; ok
    
  public SecondClassWithVeryVeryVeryLongName(String string) { // indent:2 ; exp:2; ok
    
  } // indent:2 ; exp:2; ok
    
  String getString(FooIfClass instance, int integer) { // indent:2 ; exp:2; ok
    return "String"; // indent:4 ; exp:4; ok  
  } // indent:2 ; exp:2; ok
  
  int getInteger(FooIfClass instance, String string) { // indent:2 ; exp:2; ok
    return -1;   // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  boolean getBoolean(FooIfClass instance, boolean flag) { // indent:2 ; exp:2; ok
    return false; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  SecondClassWithVeryVeryVeryLongName getInstanse() { // indent:2 ; exp:2; ok
    return new SecondClassWithVeryVeryVeryLongName("VeryLoooooooooo" // indent:4 ; exp:4; ok
        + "oongString"); // indent:8 ; exp:8; ok
  } // indent:2 ; exp:2; ok
}