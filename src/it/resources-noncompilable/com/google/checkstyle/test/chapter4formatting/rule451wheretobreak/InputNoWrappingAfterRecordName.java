package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

/** Some javadoc. */
public class InputNoWrappingAfterRecordName {
  record Multi (String string, Record rec) { // violation ''(' is preceded with whitespace'
    private boolean inRecord (Object obj) { // violation ''(' is preceded with whitespace'
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

  record Multi3 (Integer i, Node node) { // violation ''(' is preceded with whitespace'
    public static void main (String... args) { // violation ''(' is preceded with whitespace'
      System.out.println("works!");
    }
  }

  record Multi4 () { // violation ''(' is preceded with whitespace'
    void foo (){} // violation ''(' is preceded with whitespace'
  }

  record Multi5() {
    static Multi obj =
        new Multi ("my string", new Multi4()); // violation ''(' is preceded with whitespace'
  }

  class MyTestClass {
    private Multi obj =
        new Multi ("my string", new Multi4()); // violation ''(' is preceded with whitespace'
  }
}
