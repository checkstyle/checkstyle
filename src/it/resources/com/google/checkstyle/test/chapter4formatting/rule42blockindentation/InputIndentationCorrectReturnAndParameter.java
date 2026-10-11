package com.google.checkstyle.test.chapter4formatting.rule42blockindentation;

class InputIndentationCorrectReturnAndParameter {

  String getString(int someInt, String someString) {
    return "String";
  }

  boolean fooMethodWithIf() {

    return conditionSecond(
            10000000000.0,
            new SecondClassLongNam4("Looooooooooooo" + "oooooooooooong")
                .getString(
                    new FooReturnClass(),
                    new SecondClassLongNam4("loooooooooong")
                        .getInteger(new FooReturnClass(), "loooooooooooooong")),
            "loooooooooooong")
        || conditionThird(2048)
        || conditionFourth(
            new SecondClassLongNam4("Looooooooooooooo" + "ooooooooooooong")
                .getBoolean(new FooReturnClass(), false))
        || conditionFifth(
            true,
            new SecondClassLongNam4(
                    getString(2048, "Looo" + "ooooooooooooooooooooooooooooooooooooooooooong"))
                .getBoolean(new FooReturnClass(), true))
        || conditionSixth(
            false, new SecondClassLongNam4(getString(100000, "Loooooong" + "Fooooooo><")))
        || conditionNoArg()
        || conditionNoArg()
        || conditionNoArg()
        || conditionNoArg();
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

  private boolean conditionSixth(boolean flag, SecondClassLongNam4 instance) {
    return false;
  }

  private boolean conditionNoArg() {
    return false;
  }

  class InnerClassFoo {

    FooReturnClass anonymousClass =
        new FooReturnClass() {

          boolean fooMethodWithIf(
              String stringStringStringStringLooooongString,
              int intIntIntVeryLongNameForIntVariable,
              boolean fooooooooobooleanBooleanVeryLongName) {

            return conditionSecond(
                    10000000000.0,
                    new SecondClassLongNam4("Looooooooooooo" + "oooooooooooong")
                        .getString(
                            new FooReturnClass(),
                            new SecondClassLongNam4("loooooooooong")
                                .getInteger(new FooReturnClass(), "looooooooong")),
                    "loooooooooooong")
                || conditionThird(2048)
                || conditionFourth(
                    new SecondClassLongNam4("Looooooooooooooo" + "ooooooooooooong")
                        .getBoolean(new FooReturnClass(), false))
                || conditionFifth(
                    true,
                    new SecondClassLongNam4(
                            getString(2048, "Lo" + "ooooooooooooooooooooooooooooooooooooooooooong"))
                        .getBoolean(new FooReturnClass(), true))
                || conditionSixth(
                    false, new SecondClassLongNam4(getString(100000, "Loooooong" + "Fooooooo><")))
                || conditionNoArg()
                || conditionNoArg()
                || conditionNoArg()
                || conditionNoArg() && fooooooooobooleanBooleanVeryLongName;
          }

          boolean fooReturn() {
            return conditionFirst(
                "Loooooooooooooooooong",
                new SecondClassLongNam4("Loooooooooooooooooog")
                    .getInteger(new FooReturnClass(), "Loooooooooooooooooog"),
                new InnerClassFoo());
          }
        };

    boolean fooMethodWithIf() {
      return conditionFirst(
          "Loooooooooooooooooong",
          new SecondClassLongNam4("Loooooooooooooooooog")
              .getInteger(new FooReturnClass(), "Loooooooooooooooooog"),
          new InnerClassFoo());
    }

    boolean fooReturn() {
      return conditionSecond(
              10000000000.0,
              new SecondClassLongNam4("Looooooooooooo" + "oooooooooooong")
                  .getString(
                      new FooReturnClass(),
                      new SecondClassLongNam4("loooooooooong")
                          .getInteger(new FooReturnClass(), "looooooooooong")),
              "loooooooooooong")
          || conditionThird(2048)
          || conditionFourth(
              new SecondClassLongNam4("Looooooooooooooo" + "ooooooooooooong")
                  .getBoolean(new FooReturnClass(), false))
          || conditionFifth(
              true,
              new SecondClassLongNam4(
                      getString(2048, "Looo" + "ooooooooooooooooooooooooooooooooooooooooooong"))
                  .getBoolean(new FooReturnClass(), true))
          || conditionSixth(
              false, new SecondClassLongNam4(getString(100000, "Loooooong" + "Fooooooo><")))
          || conditionNoArg()
          || conditionNoArg()
          || conditionNoArg()
          || conditionNoArg();
    }
  }

  class SecondClassLongNam4 {

    public SecondClassLongNam4(String string) {}

    String getString(FooReturnClass instance, int integer) {
      return "String";
    }

    int getInteger(FooReturnClass instance, String string) {
      return -1;
    }

    boolean getBoolean(FooReturnClass instance, boolean flag) {
      return false;
    }

    SecondClassLongNam4 getInstance() {
      return new SecondClassLongNam4("VeryLoooooooooo" + "oongString");
    }
  }

  class FooReturnClass {}
}
