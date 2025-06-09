package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

/** Some javadoc. */
public class InputNoWrappingAfterRecordName {
  record Multi(String string, Record rec) {
    private boolean inRecord(Object obj) {
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

  record Multi3(Integer i, Node node) {
    public static void main(String... args) {
      System.out.println("works!");
    }
  }

  record Multi4() {
    void foo() {}
  }

  record Multi5() {
    static Multi5 obj =
        new Multi("my string", new Mtr4());
  }

  class MyTestClass {
    private Multi obj =
        new Multi("my string", new Mtr4());
  }
}
