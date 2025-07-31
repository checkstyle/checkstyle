package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

/** Some javadoc. */
public record InputFormattedNoWrappingAfterRecordName(String name) {
  record Multi(String main, Record rec) {
    private static boolean isSent(Object obj) {
      int value = 0;
      if (obj instanceof Integer i) {
        value = i;
      }
      return value > 10;
    }
  }

  record Multi2() {
    Multi2(String s1, String s2, String s3) {
      this();
    }
  }

  record Multi3(Integer i, Integer node) {
    public static void main(String... args) {
      System.out.println("works!");
    }
  }

  record Multi4() {
    void foo() {}
  }

  record Multi5() {
    private static final Multi obj = new Multi("my string", new Multi4());
  }

  record Multi6() {

    public static final Multi obj = new Multi("hello", new Multi4());
  }

  class MyTestClass {
    private final Multi obj = new Multi("my string", new Multi4());
  }

  /**
   * Maps the ports.
   *
   * @param from the from param
   */
  record Mapping(String from) {

    /**
     * The constructor for Mapping.
     *
     * @param from The source
     */
    Mapping(String from) {
      this.from = from;
    }
  }
}
