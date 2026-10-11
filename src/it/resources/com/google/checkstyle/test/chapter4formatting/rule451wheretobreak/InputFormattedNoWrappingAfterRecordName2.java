package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

/** Some javadoc. */
public class InputFormattedNoWrappingAfterRecordName2 {

  @interface InSize {
    int limit();
  }

  @InSize(limit = 2)
  record MyRecord(String main, Record rec) {
    private static boolean isSent(Object obj) {
      int value = 0;
      if (obj instanceof Integer i) {
        value = i;
      }
      return value > 10;
    }
  }

  record Multi2() {

    @InSize(limit = 2)
    public Multi2(String s1, String s2, String s3) {
      this();
    }
  }

  record Multi3(Integer i, Integer node) {
    public static void main(String... args) {
      System.out.println("works!");
    }
  }

  record Multi4() {

    @InSize(limit = 2)
    void foo() {}
  }

  record Multi5() {
    private static final MyRecord object = new MyRecord("my string", new Multi4());
  }

  record Multi6() {

    public static final MyRecord obj = new MyRecord("hello", new Multi4());
  }

  class MyTestClass {
    private final MyRecord obj = new MyRecord("my string", new Multi4());
  }

  /**
   * Maps the ports.
   *
   * @param from the from param
   */
  @InSize(limit = 2)
  record Mapping(String from) {

    /**
     * The constructor for Mapping.
     *
     * @param from The source
     */
    @InSize(limit = 3)
    Mapping(String from) {
      this.from = from;
    }
  }
}
