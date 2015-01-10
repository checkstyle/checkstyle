package com.puppycrawl.tools.checkstyle.indentation; // indent:0 ; exp:0; ok

class FooWhileClass { // indent:0 ; exp:0; ok
    
  String getString(int someInt, String someString) { // indent:2 ; exp:2; ok
    return "String"; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok

  void fooMethodWithIf() { // indent:2 ; exp:2; ok

    while (conditionFirst("Loooooooooooooooooong", new // indent:4 ; exp:4; ok
        SecondWhileClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). // indent:8 ; exp:8; ok
           getInteger(new FooWhileClass(), "Loooooooooooooooooog"), // indent:11 ; exp:>8; ok
          new InnerClassFoo())) {} // indent:10 ; exp:>8; ok
    
    do { // indent:4 ; exp:4; ok
    	
    } while (conditionFirst("Loooooooooooooooooong", new // indent:4 ; exp:4; ok
         SecondWhileClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). // indent:9 ; exp:>8; ok
               getInteger(new FooWhileClass(), "Loooooooooooooooooog"), // indent:15 ; exp:>8; ok
         new InnerClassFoo())); // indent:10 ; exp:>8; ok
    
    while (conditionSecond(10000000000.0, new // indent:4 ; exp:4; ok
         SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooo" // indent:9 ; exp:>8; ok
        + "oooooooooooong").getString(new FooWhileClass(), // indent:8 ; exp:8; ok
           new SecondWhileClassWithVeryVeryVeryLongName("loooooooooong"). // indent:11 ; exp:>8; ok
        getInteger(new FooWhileClass(), "loooooooooooooong")), "loooooooooooong") // indent:8 ; exp:8; ok
              || conditionThird(2048) || conditionFourth(new // indent:14 ; exp:>8; ok
        SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooooo" // indent:8 ; exp:8; ok
        + "ooooooooooooong").getBoolean(new FooWhileClass(), false)) || // indent:8 ; exp:8; ok
            conditionFifth(true, new SecondWhileClassWithVeryVeryVeryLongName( // indent:12 ; exp:>8; ok
            getString(2048, "Looo" // indent:12 ; exp:>8; ok
        + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( // indent:8 ; exp:8; ok
        new FooWhileClass(), true)) || conditionSixth(false, new // indent:8 ; exp:8; ok
           SecondWhileClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" // indent:11 ; exp:>8; ok
        + "Fooooooo><"))) || conditionNoArg() // indent:8 ; exp:8; ok
          || conditionNoArg() || // indent:10 ; exp:>8; ok
          conditionNoArg() || conditionNoArg()) {} // indent:8 ; exp:8; ok
    
    do { // indent:4 ; exp:4; ok
    	
    } while (conditionSecond(10000000000.0, new // indent:4 ; exp:4; ok
          SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooo" // indent:10 ; exp:>8; ok
          + "oooooooooooong").getString(new FooWhileClass(), // indent:10 ; exp:>8; ok
        new SecondWhileClassWithVeryVeryVeryLongName("loooooooooong"). // indent:8 ; exp:8; ok
             getInteger(new FooWhileClass(), "loooooooooooooong")), "loooooooooooong") // indent:13 ; exp:>8; ok
        || conditionThird(2048) || conditionFourth(new // indent:8 ; exp:8; ok
           SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooooo" // indent:11 ; exp:>8; ok
        + "ooooooooooooong").getBoolean(new FooWhileClass(), false)) || // indent:8 ; exp:8; ok
        conditionFifth(true, new SecondWhileClassWithVeryVeryVeryLongName(getString(2048, "Looo" // indent:8 ; exp:8; ok
        + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( // indent:8 ; exp:8; ok
        new FooWhileClass(), true)) || conditionSixth(false, new // indent:8 ; exp:8; ok
           SecondWhileClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" // indent:11 ; exp:>8; ok
        + "Fooooooo><"))) || conditionNoArg() // indent:8 ; exp:8; ok
             || conditionNoArg() || // indent:13 ; exp:>8; ok
        conditionNoArg() || conditionNoArg()); // indent:8 ; exp:8; ok
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
      SecondWhileClassWithVeryVeryVeryLongName instance) { // indent:6 ; exp:6; ok
    return false; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  private boolean conditionNoArg() { // indent:2 ; exp:2; ok
    return false; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  class InnerClassFoo { // indent:2 ; exp:2; ok

    void fooMethodWithIf() { // indent:4 ; exp:4; ok
      while (conditionFirst("Loooooooooooooooooong", new // indent:6 ; exp:6; ok
          SecondWhileClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). // indent:10 ; exp:10; ok
              getInteger(new FooWhileClass(), "Loooooooooooooooooog"), // indent:14 ; exp:>10; ok
              new InnerClassFoo())) {} // indent:14 ; exp:>10; ok

      do { // indent:6 ; exp:6; ok
        /* Do somethig*/
      } while (conditionFirst("Loooooooooooooooooong", new // indent:6 ; exp:6; ok
          SecondWhileClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). // indent:10 ; exp:10; ok
              getInteger(new FooWhileClass(), "Loooooooooooooooooog"), // indent:14 ; exp:>10; ok
              new InnerClassFoo())); // indent:14 ; exp:>10; ok

      while (conditionSecond(10000000000.0, new // indent:6 ; exp:6; ok
          SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooo" // indent:10 ; exp:10; ok
          + "oooooooooooong").getString(new FooWhileClass(), // indent:10 ; exp:10; ok
          new SecondWhileClassWithVeryVeryVeryLongName("loooooooooong"). // indent:10 ; exp:10; ok
          getInteger(new FooWhileClass(), "loooooooooooooong")), "loooooooooooong") // indent:10 ; exp:10; ok
          || conditionThird(2048) || conditionFourth(new // indent:10 ; exp:10; ok
          SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooooo" // indent:10 ; exp:10; ok
            + "ooooooooooooong").getBoolean(new FooWhileClass(), false)) || // indent:12 ; exp:>10; ok
            conditionFifth(true, new SecondWhileClassWithVeryVeryVeryLongName(getString(2048, "Looo" // indent:12 ; exp:>10; ok
            + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( // indent:12 ; exp:>10; ok
            new FooWhileClass(), true)) || conditionSixth(false, new // indent:12 ; exp:>10; ok
              SecondWhileClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" // indent:14 ; exp:>10; ok
              + "Fooooooo><"))) || conditionNoArg() // indent:14 ; exp:>10; ok
              || conditionNoArg() || // indent:14 ; exp:>10; ok
              conditionNoArg() || conditionNoArg()) {} // indent:14 ; exp:>10; ok
      
      do {
    	/* Do smth*/
      } while (conditionSecond(10000000000.0, new // indent:6 ; exp:6; ok
          SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooo" // indent:10 ; exp:10; ok
          + "oooooooooooong").getString(new FooWhileClass(), // indent:10 ; exp:10; ok
          new SecondWhileClassWithVeryVeryVeryLongName("loooooooooong"). // indent:10 ; exp:10; ok
          getInteger(new FooWhileClass(), "loooooooooooooong")), "loooooooooooong") // indent:10 ; exp:10; ok
          || conditionThird(2048) || conditionFourth(new // indent:10 ; exp:10; ok
          SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooooo" // indent:10 ; exp:10; ok
            + "ooooooooooooong").getBoolean(new FooWhileClass(), false)) || // indent:12 ; exp:>10; ok
            conditionFifth(true, new SecondWhileClassWithVeryVeryVeryLongName(getString(2048, "Looo" // indent:12 ; exp:>10; ok
            + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( // indent:12 ; exp:>10; ok
            new FooWhileClass(), true)) || conditionSixth(false, new // indent:12 ; exp:>10; ok
              SecondWhileClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" // indent:14 ; exp:>10; ok
              + "Fooooooo><"))) || conditionNoArg() // indent:14 ; exp:>10; ok
              || conditionNoArg() || // indent:14 ; exp:>10; ok
              conditionNoArg() || conditionNoArg()); // indent:14 ; exp:>10; ok
    } // indent:4 ; exp:4; ok

    FooWhileClass anonymousClass = new FooWhileClass() { // indent:4 ; exp:4; ok

      void fooMethodWithIf(String stringStringStringStringLooooongString, // indent:6 ; exp:6; ok
          int intIntIntVeryLongNameForIntVariable, boolean // indent:10 ; exp:10; ok
              fooooooooobooleanBooleanVeryLongName) { // indent:14 ; exp:>10; ok

        while (conditionFirst("Loooooooooooooooooong", new // indent:8 ; exp:8; ok
            SecondWhileClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). // indent:12 ; exp:12; ok
                getInteger(new FooWhileClass(), "Loooooooooooooooooog"), // indent:16 ; exp:>12; ok
                   new InnerClassFoo())) {} // indent:19 ; exp:>12; ok
        
        do { // indent:8 ; exp:8; ok
          /* Do smth*/
        } while (conditionFirst("Loooooooooooooooooong", new // indent:8 ; exp:8; ok
            SecondWhileClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). // indent:12 ; exp:12; ok
                getInteger(new FooWhileClass(), "Loooooooooooooooooog"), // indent:16 ; exp:>12; ok
                   new InnerClassFoo())); // indent:19 ; exp:>12; ok

        while (conditionSecond(10000000000.0, new // indent:8 ; exp:8; ok
            SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooo" // indent:12 ; exp:12; ok
            + "oooooooooooong").getString(new FooWhileClass(), // indent:12 ; exp:12; ok
            new SecondWhileClassWithVeryVeryVeryLongName("loooooooooong"). // indent:12 ; exp:12; ok
            getInteger(new FooWhileClass(), "loooooooooooooong")), "loooooooooooong") // indent:12 ; exp:12; ok
            || conditionThird(2048) || conditionFourth(new // indent:12 ; exp:12; ok
            SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooooo" // indent:12 ; exp:12; ok
              + "ooooooooooooong").getBoolean(new FooWhileClass(), false)) || // indent:14 ; exp:>12; ok
              conditionFifth(true, new SecondWhileClassWithVeryVeryVeryLongName(getString(2048, "Looo" // indent:14 ; exp:>12; ok
              + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( // indent:14 ; exp:>12; ok
              new FooWhileClass(), true)) || conditionSixth(false, new // indent:14 ; exp:>12; ok
                SecondWhileClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" // indent:16 ; exp:>12; ok
                + "Fooooooo><"))) || conditionNoArg() // indent:16 ; exp:>12; ok
                || conditionNoArg() || // indent:16 ; exp:>12; ok
                  conditionNoArg() || conditionNoArg() // indent:18 ; exp:>12; ok
                     && fooooooooobooleanBooleanVeryLongName) {} // indent:21 ; exp:>12; ok
        
        do { // indent:8 ; exp:8; ok
          /* Do smth*/
        } while (conditionSecond(10000000000.0, new // indent:8 ; exp:8; ok
            SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooo" // indent:12 ; exp:12; ok
            + "oooooooooooong").getString(new FooWhileClass(), // indent:12 ; exp:12; ok
            new SecondWhileClassWithVeryVeryVeryLongName("loooooooooong"). // indent:12 ; exp:12; ok
            getInteger(new FooWhileClass(), "loooooooooooooong")), "loooooooooooong") // indent:12 ; exp:12; ok
            || conditionThird(2048) || conditionFourth(new // indent:12 ; exp:12; ok
            SecondWhileClassWithVeryVeryVeryLongName("Looooooooooooooo" // indent:12 ; exp:12; ok
              + "ooooooooooooong").getBoolean(new FooWhileClass(), false)) || // indent:14 ; exp:>12; ok
              conditionFifth(true, new SecondWhileClassWithVeryVeryVeryLongName(getString(2048, "Looo" // indent:14 ; exp:>12; ok
              + "ooooooooooooooooooooooooooooooooooooooooooong")).getBoolean( // indent:14 ; exp:>12; ok
              new FooWhileClass(), true)) || conditionSixth(false, new // indent:14 ; exp:>12; ok
                SecondWhileClassWithVeryVeryVeryLongName(getString(100000, "Loooooong" // indent:16 ; exp:>12; ok
                + "Fooooooo><"))) || conditionNoArg() // indent:16 ; exp:>12; ok
                || conditionNoArg() || // indent:16 ; exp:>12; ok
                  conditionNoArg() || conditionNoArg() // indent:18 ; exp:>12; ok
                     && fooooooooobooleanBooleanVeryLongName);// indent:21 ; exp:>12; ok
      } // indent:6 ; exp:6; ok
    }; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
}

class SecondWhileClassWithVeryVeryVeryLongName { // indent:0 ; exp:0; ok
    
  public SecondWhileClassWithVeryVeryVeryLongName(String string) { // indent:2 ; exp:2; ok
    
  } // indent:2 ; exp:2; ok
    
  String getString(FooWhileClass instance, int integer) { // indent:2 ; exp:2; ok
    return "String"; // indent:4 ; exp:4; ok  
  } // indent:2 ; exp:2; ok
  
  int getInteger(FooWhileClass instance, String string) { // indent:2 ; exp:2; ok
    return -1;   // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  boolean getBoolean(FooWhileClass instance, boolean flag) { // indent:2 ; exp:2; ok
    return false; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  SecondWhileClassWithVeryVeryVeryLongName getInstanse() { // indent:2 ; exp:2; ok
    return new SecondWhileClassWithVeryVeryVeryLongName("VeryLoooooooooo" // indent:4 ; exp:4; ok
        + "oongString"); // indent:8 ; exp:8; ok
  } // indent:2 ; exp:2; ok
}