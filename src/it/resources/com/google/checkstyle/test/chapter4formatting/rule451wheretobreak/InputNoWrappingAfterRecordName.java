package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

/** Some javadoc. */
public record InputNoWrappingAfterRecordName (String name) {
  // violation above ''(' is preceded with whitespace'
  record Multi (String main, Record rec) { // violation ''(' is preceded with whitespace'
    private static boolean isSent (Object obj) { // violation ''(' is preceded with whitespace'
      int value = 0;
      if (obj instanceof Integer i) {
        value = i;
      }
      return value > 10;
    }
  }

  record Multi2 () { // violation ''(' is preceded with whitespace'
    Multi2 (String s1, String s2, String s3) { // violation ''(' is preceded with whitespace'
      this (); // violation ''(' is preceded with whitespace'
    }
  }

  record Multi3 (Integer i, Integer node) { // violation ''(' is preceded with whitespace'
    public static void main (String... args) { // violation ''(' is preceded with whitespace'
      System.out.println("works!");
    }
  }

  record Multi4 () { // violation ''(' is preceded with whitespace'
    void foo (){} // violation ''(' is preceded with whitespace'
  }

  record Multi5() {
    private static final Multi obj =
        new Multi ("my string", new Multi4()); // violation ''(' is preceded with whitespace'
  }

  // violation 2 lines below ''(' has incorrect indentation level 2, expected level should be 6.'
  record Multi6
  () {
    // violation above ''(' should be on the previous line.'

    // violation 2 lines below ''(' has incorrect indentation level 4, expected level should be 8.'
    public static final Multi obj = new Multi
    ("hello", new Multi4 // violation ''(' should be on the previous line.'
    ());
    // violation above ''(' has incorrect indentation level 4, expected level should be 8.'
    // violation 2 lines above ''(' should be on the previous line.'
  }

  class MyTestClass {
    private final Multi obj =
        new Multi ("my string", new Multi4()); // violation ''(' is preceded with whitespace'
  }

  /**
   * Maps the ports.
   *
   * @param from the from param
   */
  record Mapping (String from) { // violation ''(' is preceded with whitespace'

    /**
     * The constructor for Mapping.
     *
     * @param from The source
     */
    Mapping (String from) { // violation ''(' is preceded with whitespace'
      this.from = from;
    }
  }
}
