package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

/** Some javadoc. */
public class InputNoWrappingAfterRecordName2 {

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

  record Multi2 () { // violation ''(' is preceded with whitespace'

    @InSize(limit = 2)
    public Multi2 (String s1, String s2, String s3) { // violation ''(' is preceded with whitespace'
      this (); // violation ''(' is preceded with whitespace'
    }
  }

  record Multi3 (Integer i, Integer node) { // violation ''(' is preceded with whitespace'
    public static void main (String... args) { // violation ''(' is preceded with whitespace'
      System.out.println("works!");
    }
  }

  record Multi4 () { // violation ''(' is preceded with whitespace'

    @InSize(limit = 2)
    void foo (){} // violation ''(' is preceded with whitespace'
    // violation above 'WhitespaceAround: '{' is not preceded with whitespace'
  }

  record Multi5() {
    private static final MyRecord object =
        new MyRecord ("my string", new Multi4()); // violation ''(' is preceded with whitespace'
  }

  // violation 2 lines below ''(' has incorrect indentation level 2, expected level should be 6.'
  record Multi6
  () {
    // violation above ''(' should be on the previous line.'

    // violation 2 lines below ''(' has incorrect indentation level 4, expected level should be 8.'
    public static final MyRecord obj = new MyRecord
    ("hello", new Multi4 // violation ''(' should be on the previous line.'
    ());
    // violation above ''(' has incorrect indentation level 4, expected level should be 8.'
    // violation 2 lines above ''(' should be on the previous line.'
  }

  class MyTestClass {
    private final MyRecord obj =
        new MyRecord ("my string", new Multi4()); // violation ''(' is preceded with whitespace'
  }

  /**
   * Maps the ports.
   *
   * @param from the from param
   */
  @InSize(limit = 2)
  record Mapping (String from) { // violation ''(' is preceded with whitespace'

    /**
     * The constructor for Mapping.
     *
     * @param from The source
     */
    @InSize(limit = 3)
    Mapping (String from) { // violation ''(' is preceded with whitespace'
      this.from = from;
    }
  }
}
