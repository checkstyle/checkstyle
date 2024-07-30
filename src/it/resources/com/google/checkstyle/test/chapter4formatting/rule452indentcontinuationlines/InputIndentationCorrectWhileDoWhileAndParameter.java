package com.google.checkstyle.test.chapter4formatting.rule452indentcontinuationlines;

class InputIndentationCorrectWhileDoWhileAndParameter {

  String getString(int someInt, String someString) {
    return "String";
  }

  void fooMethodWithIf() {

    while (conditionFirst(
        "Loooooooooooooooooong",
        new SecondWhileLongNam1("Loooooooooooooooooog")
            .getInteger(new FooWhileClass(), "Loooooooooooooooooog"),
        new InnerClassFoo())) {}

    do {} while (conditionFirst(
        "Loooooooooooooooooong",
        new SecondWhileLongNam1("Loooooooooooooooooog")
            .getInteger(new FooWhileClass(), "Loooooooooooooooooog"),
        new InnerClassFoo()));

    while (conditionSecond(
            10000000000.0,
            new SecondWhileLongNam1("Looooooooooooo" + "oooooooooooong")
                .getString(
                    new FooWhileClass(),
                    new SecondWhileLongNam1("loooooooooong")
                        .getInteger(new FooWhileClass(), "loooooooooooooong")),
            "loooooooooooong")
        || conditionThird(2048)
        || conditionFourth(
            new SecondWhileLongNam1("Looooooooooooooo" + "ooooooooooooong")
                .getBoolean(new FooWhileClass(), false))
        || conditionFifth(
            true,
            new SecondWhileLongNam1(
                    getString(2048, "Looo" + "ooooooooooooooooooooooooooooooooooooooooooong"))
                .getBoolean(new FooWhileClass(), true))
        || conditionSixth(
            false, new SecondWhileLongNam1(getString(100000, "Loooooong" + "Fooooooo><")))
        || conditionNoArg()
        || conditionNoArg()
        || conditionNoArg()
        || conditionNoArg()) {}

    do {} while (conditionSecond(
            10000000000.0,
            new SecondWhileLongNam1("Looooooooooooo" + "oooooooooooong")
                .getString(
                    new FooWhileClass(),
                    new SecondWhileLongNam1("loooooooooong")
                        .getInteger(new FooWhileClass(), "loooooooong")),
            "loooooooooooong")
        || conditionThird(2048)
        || conditionFourth(
            new SecondWhileLongNam1("Looooooooooooooo" + "ooooooooooooong")
                .getBoolean(new FooWhileClass(), false))
        || conditionFifth(
            true,
            new SecondWhileLongNam1(
                    getString(2048, "Looo" + "ooooooooooooooooooooooooooooooooooooooooooong"))
                .getBoolean(new FooWhileClass(), true))
        || conditionSixth(
            false, new SecondWhileLongNam1(getString(100000, "Loooooong" + "Fooooooo><")))
        || conditionNoArg()
        || conditionNoArg()
        || conditionNoArg()
        || conditionNoArg());
  }

  private boolean conditionFirst(String longString, int integer, InnerClassFoo someInstance) {
    return false;
  }

  private boolean conditionSecond(
      double longLongLongDoubleValue, String longLongLongString, String secondLongLongString) {
    return false;
  }

  private boolean conditionThird(long veryLongValue) {
    return false;
  }

  private boolean conditionFourth(boolean flag) {
    return false;
  }

  private boolean conditionFifth(boolean flag1, boolean flag2) {
    return false;
  }

  private boolean conditionSixth(boolean flag, SecondWhileLongNam1 instance) {
    return false;
  }

  private boolean conditionNoArg() {
    return false;
  }

  class InnerClassFoo {

    FooWhileClass anonymousClass =
        new FooWhileClass() {

          void fooMethodWithIf(
              String stringStringStringStringLooooongString,
              int intIntIntVeryLongNameForIntVariable,
              boolean fooooooooobooleanBooleanVeryLongName) {

            while (conditionFirst(
                "Loooooooooooooooooong",
                new SecondWhileLongNam1("Loooooooooooooooooog")
                    .getInteger(new FooWhileClass(), "Loooooooooooooooooog"),
                new InnerClassFoo())) {}

            do {
              /* Do smth*/

            } while (conditionFirst(
                "Loooooooooooooooooong",
                new SecondWhileLongNam1("Loooooooooooooooooog")
                    .getInteger(new FooWhileClass(), "Loooooooooooooooooog"),
                new InnerClassFoo()));

            while (conditionSecond(
                    10000000000.0,
                    new SecondWhileLongNam1("Looooooooooooo" + "oooooooooooong")
                        .getString(
                            new FooWhileClass(),
                            new SecondWhileLongNam1("loooooooooong")
                                .getInteger(new FooWhileClass(), "loooooooooong")),
                    "loooooooooooong")
                || conditionThird(2048)
                || conditionFourth(
                    new SecondWhileLongNam1("Looooooooooooooo" + "ooooooooooooong")
                        .getBoolean(new FooWhileClass(), false))
                || conditionFifth(
                    true,
                    new SecondWhileLongNam1(
                            getString(2048, "Lo" + "ooooooooooooooooooooooooooooooooooooooooooong"))
                        .getBoolean(new FooWhileClass(), true))
                || conditionSixth(
                    false, new SecondWhileLongNam1(getString(100000, "Loooooong" + "Fooooooo><")))
                || conditionNoArg()
                || conditionNoArg()
                || conditionNoArg()
                || conditionNoArg() && fooooooooobooleanBooleanVeryLongName) {}

            do {
              /* Do smth*/

            } while (conditionSecond(
                    10000000000.0,
                    new SecondWhileLongNam1("Looooooooooooo" + "oooooooooooong")
                        .getString(
                            new FooWhileClass(),
                            new SecondWhileLongNam1("loooooooooong")
                                .getInteger(new FooWhileClass(), "loooooooooong")),
                    "loooooooooooong")
                || conditionThird(2048)
                || conditionFourth(
                    new SecondWhileLongNam1("Looooooooooooooo" + "ooooooooooooong")
                        .getBoolean(new FooWhileClass(), false))
                || conditionFifth(
                    true,
                    new SecondWhileLongNam1(
                            getString(2048, "Lo" + "ooooooooooooooooooooooooooooooooooooooooooong"))
                        .getBoolean(new FooWhileClass(), true))
                || conditionSixth(
                    false, new SecondWhileLongNam1(getString(100000, "Loooooong" + "Fooooooo><")))
                || conditionNoArg()
                || conditionNoArg()
                || conditionNoArg()
                || conditionNoArg() && fooooooooobooleanBooleanVeryLongName);
          }
        };

    void fooMethodWithIf() {
      while (conditionFirst(
          "Loooooooooooooooooong",
          new SecondWhileLongNam1("Loooooooooooooooooog")
              .getInteger(new FooWhileClass(), "Loooooooooooooooooog"),
          new InnerClassFoo())) {}

      do {
        /* Do something*/

      } while (conditionFirst(
          "Loooooooooooooooooong",
          new SecondWhileLongNam1("Loooooooooooooooooog")
              .getInteger(new FooWhileClass(), "Loooooooooooooooooog"),
          new InnerClassFoo()));

      while (conditionSecond(
              10000000000.0,
              new SecondWhileLongNam1("Looooooooooooo" + "oooooooooooong")
                  .getString(
                      new FooWhileClass(),
                      new SecondWhileLongNam1("loooooooooong")
                          .getInteger(new FooWhileClass(), "loooooooooooong")),
              "loooooooooooong")
          || conditionThird(2048)
          || conditionFourth(
              new SecondWhileLongNam1("Looooooooooooooo" + "ooooooooooooong")
                  .getBoolean(new FooWhileClass(), false))
          || conditionFifth(
              true,
              new SecondWhileLongNam1(
                      getString(2048, "Looo" + "ooooooooooooooooooooooooooooooooooooooooooong"))
                  .getBoolean(new FooWhileClass(), true))
          || conditionSixth(
              false, new SecondWhileLongNam1(getString(100000, "Loooooong" + "Fooooooo><")))
          || conditionNoArg()
          || conditionNoArg()
          || conditionNoArg()
          || conditionNoArg()) {}

      do {
        /* Do smth*/

      } while (conditionSecond(
              10000000000.0,
              new SecondWhileLongNam1("Looooooooooooo" + "oooooooooooong")
                  .getString(
                      new FooWhileClass(),
                      new SecondWhileLongNam1("loooooooooong")
                          .getInteger(new FooWhileClass(), "loooooooooooong")),
              "loooooooooooong")
          || conditionThird(2048)
          || conditionFourth(
              new SecondWhileLongNam1("Looooooooooooooo" + "ooooooooooooong")
                  .getBoolean(new FooWhileClass(), false))
          || conditionFifth(
              true,
              new SecondWhileLongNam1(
                      getString(2048, "Looo" + "ooooooooooooooooooooooooooooooooooooooooooong"))
                  .getBoolean(new FooWhileClass(), true))
          || conditionSixth(
              false, new SecondWhileLongNam1(getString(100000, "Loooooong" + "Fooooooo><")))
          || conditionNoArg()
          || conditionNoArg()
          || conditionNoArg()
          || conditionNoArg());
    }
  }

  class SecondWhileLongNam1 {

    public SecondWhileLongNam1(String string) {}

    String getString(FooWhileClass instance, int integer) {
      return "String";
    }

    int getInteger(FooWhileClass instance, String string) {
      return -1;
    }

    boolean getBoolean(FooWhileClass instance, boolean flag) {
      return false;
    }

    SecondWhileLongNam1 getInstance() {
      return new SecondWhileLongNam1("VeryLoooooooooo" + "oongString");
    }
  }

  class FooWhileClass {}
}
