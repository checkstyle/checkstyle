package com.google.checkstyle.test.chapter7javadoc.rule73wherejavadocrequired;

/** some javadoc. */
public record InputFormattedJavadocTypeOnRecord(String name) {

  /** some javadoc. */
  public record Multi(String main, Record rec) {
    private static boolean isSent(int obj) {
      return obj > 10;
    }
  }

  /** some javadoc. */
  public record Multi2() {
    Multi2(String s1, String s2, String s3) {
      this();
    }
  }

  /** some javadoc. */
  public record Multi3() {
    private static final Multi2 obj = new Multi2();
  }

  class MyTestClass {
    private final Multi2 obj = new Multi2();
  }

  /** some javadoc. */
  public record Mapping(String from) {

    /** some javadoc. */
    public Mapping(String from) {
      this.from = from;
    }
  }

  /** some javadoc. */
  protected record Multi4(String s1, String s2) {

    protected Multi4 {}
  }

  /** some javadoc. */
  protected record Multi5(String s1, String s2) {

    int test1(int k) {
      return k++;
    }

    protected Multi5 {}
  }
}
