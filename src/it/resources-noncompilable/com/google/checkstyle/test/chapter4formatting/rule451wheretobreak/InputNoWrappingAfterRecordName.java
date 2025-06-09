package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

/** some javadoc. */
public class InputNoWrappingAfterRecordName {
  record Mtr (String string, Record rec) { // violation ''(' is preceded with whitespace'
    private boolean inRecord (Object obj) { // violation ''(' is preceded with whitespace'
      int value = 0;
      if (obj instanceof Integer i) {
        value = i;
      }
      return value > 10;
    }
  }

  record Mtr2 () { // violation ''(' is preceded with whitespace'
    Mtr2 (String s1, String s2, String s3) { // violation ''(' is preceded with whitespace'
      this (); // violation ''(' is preceded with whitespace'
    }
  }

  record Mtr3 (Integer i, Node node) { // violation ''(' is preceded with whitespace'
    public static void main (String... args) { // violation ''(' is preceded with whitespace'
      System.out.println("works!");
    }
  }

  record Mtr4 () { // violation ''(' is preceded with whitespace'
    void foo (){} // violation ''(' is preceded with whitespace'
  }

  record Mtr5() {
    static Mtr obj =
        new Mtr ("my string", new Mtr4()); // violation ''(' is preceded with whitespace'
  }

  class MyTestClass {
    private Mtr obj =
        new Mtr ("my string", new Mtr4()); // violation ''(' is preceded with whitespace'
  }
}
