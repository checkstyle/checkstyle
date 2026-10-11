package com.google.checkstyle.test.chapter4formatting.rule452indentcontinuationlines;

class InputIndentationCorrectIfAndParameter {

  String getString(int someInt, String someString) {
    return "String";
  }

  void fooMethodWithIf() {

    if (conditionFirst(
        "Loooooooooooooooooong",
        new SecondClassLongNam3("Loooooooooooooooooog")
            .getInteger(new FooIfClass(), "Loooooooooooooooooog"),
        new InnerClassFoo())) {
      /* foo */
    }

    if (conditionSecond(
            10000000000.0,
            new SecondClassLongNam3("Looooooooooooo" + "oooooooooooong")
                .getString(
                    new FooIfClass(),
                    new SecondClassLongNam3("loooooooooong")
                        .getInteger(new FooIfClass(), "loooooooooooooong")),
            "loooooooooooong")
        || conditionThird(2048)
        || conditionFourth(
            new SecondClassLongNam3("Looooooooooooooo" + "ooooooooooooong")
                .getBoolean(new FooIfClass(), false))
        || conditionFifth(
            true,
            new SecondClassLongNam3(
                    getString(2048, "Looo" + "ooooooooooooooooooooooooooooooooooooooooooong"))
                .getBoolean(new FooIfClass(), true))
        || conditionSixth(
            false, new SecondClassLongNam3(getString(100000, "Loooooong" + "Fooooooo><")))
        || conditionNoArg()
        || conditionNoArg()
        || conditionNoArg()
        || conditionNoArg()) {
      /* foo */
    }
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

  private boolean conditionSixth(boolean flag, SecondClassLongNam3 instance) {
    return false;
  }

  private boolean conditionNoArg() {
    return false;
  }

  class InnerClassFoo {

    FooIfClass anonymousClass =
        new FooIfClass() {

          void fooMethodWithIf(
              String stringStringStringStringLooooongString,
              int intIntIntVeryLongNameForIntVariable,
              boolean fooooooooobooleanBooleanVeryLongName) {

            if (conditionFirst(
                "Loooooooooooooooooong",
                new SecondClassLongNam3("Loooooooooooooooooog")
                    .getInteger(new FooIfClass(), "Loooooooooooooooooog"),
                new InnerClassFoo())) {
              /* foo */
            }

            if (conditionSecond(
                    10000000000.0,
                    new SecondClassLongNam3("Looooooooooooo" + "oooooooooooong")
                        .getString(
                            new FooIfClass(),
                            new SecondClassLongNam3("loooooooooong")
                                .getInteger(new FooIfClass(), "looooooooooooong")),
                    "loooooooooooong")
                || conditionThird(2048)
                || conditionFourth(
                    new SecondClassLongNam3("Looooooooooooooo" + "ooooooooooooong")
                        .getBoolean(new FooIfClass(), false))
                || conditionFifth(
                    true,
                    new SecondClassLongNam3(
                            getString(2048, "Lo" + "ooooooooooooooooooooooooooooooooooooooooooong"))
                        .getBoolean(new FooIfClass(), true))
                || conditionSixth(
                    false, new SecondClassLongNam3(getString(100000, "Loooooong" + "Fooooooo><")))
                || conditionNoArg()
                || conditionNoArg()
                || conditionNoArg()
                || conditionNoArg() && fooooooooobooleanBooleanVeryLongName) {
              /* foo */
            }
          }
        };

    void fooMethodWithIf() {
      if (conditionFirst(
          "Loooooooooooooooooong",
          new SecondClassLongNam3("Loooooooooooooooooog")
              .getInteger(new FooIfClass(), "Loooooooooooooooooog"),
          new InnerClassFoo())) {
        /* foo */
      }

      if (conditionSecond(
              10000000000.0,
              new SecondClassLongNam3("Looooooooooooo" + "oooooooooooong")
                  .getString(
                      new FooIfClass(),
                      new SecondClassLongNam3("loooooooooong")
                          .getInteger(new FooIfClass(), "loooooooooooooong")),
              "loooooooooooong")
          || conditionThird(2048)
          || conditionFourth(
              new SecondClassLongNam3("Looooooooooooooo" + "ooooooooooooong")
                  .getBoolean(new FooIfClass(), false))
          || conditionFifth(
              true,
              new SecondClassLongNam3(
                      getString(2048, "Looo" + "ooooooooooooooooooooooooooooooooooooooooooong"))
                  .getBoolean(new FooIfClass(), true))
          || conditionSixth(
              false, new SecondClassLongNam3(getString(100000, "Loooooong" + "Fooooooo><")))
          || conditionNoArg()
          || conditionNoArg()
          || conditionNoArg()
          || conditionNoArg()) {
        /* foo */
      }
    }
  }

  class SecondClassLongNam3 {

    public SecondClassLongNam3(String string) {}

    String getString(FooIfClass instance, int integer) {
      return "String";
    }

    int getInteger(FooIfClass instance, String string) {
      return -1;
    }

    boolean getBoolean(FooIfClass instance, boolean flag) {
      return false;
    }

    SecondClassLongNam3 getInstance() {
      return new SecondClassLongNam3("VeryLoooooooooo" + "oongString");
    }
  }

  class FooIfClass {}
}
