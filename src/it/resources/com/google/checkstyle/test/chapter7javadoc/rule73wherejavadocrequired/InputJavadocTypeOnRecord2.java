package com.google.checkstyle.test.chapter7javadoc.rule73wherejavadocrequired;

// violation below 'Missing a Javadoc comment.'
public class InputJavadocTypeOnRecord2 {

  @interface Example {
    String reason();
  }

  // violation below 'Missing a Javadoc comment.'
  @Example(reason = "...")
  public record Multi6(String main, Record rec) {
    private static boolean isSent(int obj) {
      return obj > 10;
    }
  }

  // violation below 'Missing a Javadoc comment.'
  @Example(reason = "...")
  public record Multi7() {
    Multi7(String s1, String s2, String s3) {
      this();
    }
  }

  // violation below 'Missing a Javadoc comment.'
  @Example(reason = "...")
  public record Multi8() {
    private static final Multi7 obj = new Multi7();
  }

  class MyTestClass {
    private final Multi7 obj = new Multi7();
  }

  // violation below 'Missing a Javadoc comment.'
  @Example(reason = "...")
  public record Mapping1(String from) {

    // violation below 'Missing a Javadoc comment.'
    @Example(reason = "...")
    public Mapping1(String from) {
      this.from = from;
    }
  }

  // violation below 'Missing a Javadoc comment.'
  @Example(reason = "...")
  protected record Multi9(String s1, String s2) {

    @Example(reason = "...")
    protected Multi9 {
    }
  }

  // violation below 'Missing a Javadoc comment.'
  @Example(reason = "...")
  protected record Multi10(String s1, String s2) {

    int test1(int k) {
      return k++;
    }

    @Example(reason = "...")
    protected Multi10 {
    }
  }
}
