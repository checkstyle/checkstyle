package com.google.checkstyle.test.chapter4formatting.rule42blockindentation;

class InputIndentationCorrectFieldAndParameter {

  boolean flag =
      conditionFirst(
          "Loooooooooooooooooong",
          new SecondFieldLongNam2("Loooooooooooooooooog")
              .getInteger(new FooFieldClass(), "Loooooooooooooooooog"),
          new InnerClassFoo());

  boolean secondFlag =
      conditionSecond(
              10000000000.0,
              new SecondFieldLongNam2("Looooooooooooo" + "oooooooooooong")
                  .getString(
                      new FooFieldClass(),
                      new SecondFieldLongNam2("loooooooooong")
                          .getInteger(new FooFieldClass(), "loooooooooooooong")),
              "loooooooooooong")
          || conditionThird(2048)
          || conditionFourth(
              new SecondFieldLongNam2("Looooooooooooooo" + "ooooooooooooong")
                  .getBoolean(new FooFieldClass(), false))
          || conditionFifth(
              true,
              new SecondFieldLongNam2(
                      getString(2048, "Looo" + "ooooooooooooooooooooooooooooooooooooooooooong"))
                  .getBoolean(new FooFieldClass(), true))
          || conditionSixth(
              false, new SecondFieldLongNam2(getString(100000, "Loooooong" + "Fooooooo><")))
          || conditionNoArg()
          || conditionNoArg()
          || conditionNoArg()
          || conditionNoArg();

  String getString(int someInt, String someString) {
    return "String";
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

  private boolean conditionSixth(boolean flag, SecondFieldLongNam2 instance) {
    return false;
  }

  private boolean conditionNoArg() {
    return false;
  }

  class InnerClassFoo {

    boolean flag =
        conditionFirst(
            "Loooooooooooooooooong",
            new SecondFieldLongNam2("Loooooooooooooooooog")
                .getInteger(new FooFieldClass(), "Loooooooooooooooooog"),
            new InnerClassFoo());

    boolean secondFlag =
        conditionSecond(
                10000000000.0,
                new SecondFieldLongNam2("Looooooooooooo" + "oooooooooooong")
                    .getString(
                        new FooFieldClass(),
                        new SecondFieldLongNam2("loooooooooong")
                            .getInteger(new FooFieldClass(), "looooooooong")),
                "loooooooooooong")
            || conditionThird(2048)
            || conditionFourth(
                new SecondFieldLongNam2("Looooooooooooooo" + "ooooooooooooong")
                    .getBoolean(new FooFieldClass(), false))
            || conditionFifth(
                true,
                new SecondFieldLongNam2(
                        getString(2048, "Looo" + "ooooooooooooooooooooooooooooooooooooooooooong"))
                    .getBoolean(new FooFieldClass(), true))
            || conditionSixth(
                false, new SecondFieldLongNam2(getString(100000, "Loooooong" + "Fooooooo><")))
            || conditionNoArg()
            || conditionNoArg()
            || conditionNoArg()
            || conditionNoArg();

    FooFieldClass anonymousClass =
        new FooFieldClass() {
          final boolean secondFlag =
              conditionSecond(
                      10000000000.0,
                      new SecondFieldLongNam2("Looooooooooooo" + "oooooooooooong")
                          .getString(
                              new FooFieldClass(),
                              new SecondFieldLongNam2("loooooooooong")
                                  .getInteger(new FooFieldClass(), "looooooong")),
                      "loooooooooooong")
                  || conditionThird(2048)
                  || conditionFourth(
                      new SecondFieldLongNam2("Looooooooooooooo" + "ooooooooooooong")
                          .getBoolean(new FooFieldClass(), false))
                  || conditionFifth(
                      true,
                      new SecondFieldLongNam2(
                              getString(
                                  2048, "Looo" + "ooooooooooooooooooooooooooooooooooooooooooong"))
                          .getBoolean(new FooFieldClass(), true))
                  || conditionSixth(
                      false, new SecondFieldLongNam2(getString(100000, "Loooooong" + "Fooooooo><")))
                  || conditionNoArg()
                  || conditionNoArg()
                  || conditionNoArg()
                  || conditionNoArg();
        };
  }

  class SecondFieldLongNam2 {

    public SecondFieldLongNam2(String string) {}

    String getString(FooFieldClass instance, int integer) {
      return "String";
    }

    int getInteger(FooFieldClass instance, String string) {
      return -1;
    }

    boolean getBoolean(FooFieldClass instance, boolean flag) {
      return false;
    }

    SecondFieldLongNam2 getInstance() {
      return new SecondFieldLongNam2("VeryLoooooooooo" + "oongString");
    }
  }

  class FooFieldClass {}
}
