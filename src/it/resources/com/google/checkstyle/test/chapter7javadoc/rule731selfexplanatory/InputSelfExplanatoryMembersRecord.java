package com.google.checkstyle.test.chapter7javadoc.rule731selfexplanatory;

/** some javadoc. */
public record InputSelfExplanatoryMembersRecord(int scoreBoard) {

  public static int score = 0;

  /** some javadoc. */
  public InputSelfExplanatoryMembersRecord {}

  /** some javadoc. */
  public void myScore() {}

  public void setScoreBoard(int score) {
    this.score = score;
  }

  public int getScoreBoard() {
    return score;
  }

  /** some javadoc. */
  public int getFoo() {
    return scoreBoard * 2;
  }

  /** some javadoc. */
  @Override
  public String toString() {
    return "failed to get score";
  }

  // without comments

  int foo1() {
    return 1;
  }

  String foo2() {
    return "Fooooooooooooooo" + "oooooooo";
  }

  // violation below 'Missing a Javadoc comment.'
  public String foo3() {
    // making the method
    // here
    // here
    return "Fooooooooooooooo" + "ooooo" + "ooo";
  }

  // ok, private methods do not require javadoc
  private String correct(String param) {
    return "Fooooooooooooooo" + "ooooo" + "ooo" + param;
  }

  // ok, default scope method does not require javadoc
  String defaultScope(int x) {
    return "Fooooooooooooooo" + "ooooo" + "ooo" + x;
  }

  /** some javadoc. */
  public void smallMethod1() {
    foo2();
  }

  /** some javadoc. */
  protected void smallMethod2() {
    foo2();
  }

  /** some javadoc. */
  public String testingParams(String param1, String param2) {
    return "Fooooooooooooooo" + "ooooo" + "ooo" + param1 + param2;
  }

  /** Ok, missing params tags and return tags in javadoc are allowed. */
  protected String testingParams(int param1, int param2) {
    return "Fooooooooooooooo" + "ooooo" + "ooo" + param1 + param2;
  }

  // violation below 'Missing a Javadoc comment.'
  public void getRunFunc() {
    foo2();
    foo1();
    foo2();
  }

  private void getRunFunc2(float x) {
    foo2();
    foo3();
    foo2();
  }

  void getRunFunc3(int a) {
    foo2();
    foo1();
    foo3();
  }
}
