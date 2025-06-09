package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

/** Some javdoc. */
public class InputNoWrappingAfterRecordName {
  record Mtr(String string, Record rec) {
    private boolean inRecord(Object obj) {
      int value = 0;
      if (obj instanceof Integer i) {
        value = i;
      }
      return value > 10;
    }
  }

  record Mtr2() {
    Mtr2(String s1, String s2, String s3) {
      this();
    }
  }

  record Mtr3(Integer i, Node node) {
    public static void main(String... args) {
      System.out.println("works!");
    }
  }

  record Mtr4() {
    void foo() {}
  }

  record Mtr5() {
    static Mtr obj =
        new Mtr("my string", new Mtr4());
  }

  class MyTestClass {
    private Mtr obj =
        new Mtr("my string", new Mtr4());
  }
}
