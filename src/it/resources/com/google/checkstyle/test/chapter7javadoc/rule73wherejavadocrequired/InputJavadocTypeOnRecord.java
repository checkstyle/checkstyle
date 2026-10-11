package com.google.checkstyle.test.chapter7javadoc.rule73wherejavadocrequired;

// violation below 'Missing a Javadoc comment.'
public record InputJavadocTypeOnRecord(String name) {

  // violation below 'Missing a Javadoc comment.'
  public record Multi(String main, Record rec) {
    private static boolean isSent(int obj) {
      return obj > 10;
    }
  }

  // violation below 'Missing a Javadoc comment.'
  public record Multi2() {
    Multi2(String s1, String s2, String s3) {
      this();
    }
  }

  // violation below 'Missing a Javadoc comment.'
  public record Multi3() {
    private static final Multi2 obj = new Multi2();
  }

  class MyTestClass {
    private final Multi2 obj = new Multi2();
  }

  // violation below 'Missing a Javadoc comment.'
  public record Mapping(String from) {

    // violation below 'Missing a Javadoc comment.'
    public Mapping(String from) {
      this.from = from;
    }
  }

  // violation below 'Missing a Javadoc comment.'
  protected record Multi4(String s1, String s2) {

    protected Multi4 {}
  }

  // violation below 'Missing a Javadoc comment.'
  protected record Multi5(String s1, String s2) {

    int test1(int k) {
      return k++;
    }

    protected Multi5 {}
  }
}
