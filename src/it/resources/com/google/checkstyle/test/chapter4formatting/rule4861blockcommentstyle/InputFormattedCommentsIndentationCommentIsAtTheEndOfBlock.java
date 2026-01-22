package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;

/** Contains examples of using comments at the end of the block. */
public class InputFormattedCommentsIndentationCommentIsAtTheEndOfBlock {

  /** Some javadoc. */
  public void foo1() {
    foo2();
    // OOOO: missing functionality
  }

  /** Some javadoc. */
  public void foo2() {

    foo3();
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo3() {
    foo2();
    // refreshDisplay();
  }

  /** Some javadoc. */
  public void foo4() {
    foooooooooooooooooooooooooooooooooooooooooo();
    // ^-- some hint
  }

  /** Some javadoc. */
  public void foooooooooooooooooooooooooooooooooooooooooo() {}

  /////////////////////////////// odd indentation comment

  /** Some javadoc. */
  public void foo7() {

    int a = 0;
    // odd indentation comment
  }

  /////////////////////////////// (a single-line border to separate a group of methods)

  /** Some javadoc. */
  public void foo8() {}

  /** Some javadoc. */
  public class TestClass {
    /** Some javadoc. */
    public void test() {

      int a = 0;
      // odd indentation comment
    }
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo9() {

    this.foo1();
    // odd indentation comment
  }

  //    public void foo10() {
  //
  //    }

  /** Some javadoc. */
  public void foo11() {
    String.valueOf(new Integer(0)).trim().length();
    // comment
  }

  /** Some javadoc. */
  public void foo12() {

    String.valueOf(new Integer(0)).trim().length();
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo13() {
    String.valueOf(new Integer(0)).trim().length();
    // comment
  }

  /** Some javadoc. */
  public void foo14() {

    String.valueOf(new Integer(0)).trim().length();
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo15() {
    String.valueOf(new Integer(0));
    // comment
  }

  /** Some javadoc. */
  public void foo16() {

    String.valueOf(new Integer(0));
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo17() {
    String.valueOf(new Integer(0))
        .trim()
        // comment
        .length();
  }

  /** Some javadoc. */
  public void foo18() {

    String.valueOf(new Integer(0))
        .trim()
        // odd indentation comment
        .length();
  }

  /** Some javadoc. */
  public void foo19() {
    (new Thread(
            new Runnable() {
              @Override
              public void run() {}
            }))
        .run();
    // comment
  }

  /** Some javadoc. */
  public void foo20() {

    (new Thread(
            new Runnable() {
              @Override
              public void run() {}
            }))
        .run();
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo21() {
    int[] array = new int[5];

    java.util.List<String> expected = new java.util.ArrayList<>();
    for (int i = 0; i < 5; i++) {
      org.junit.Assert.assertEquals(expected.get(i), array[i]);
    }
    String s =
        String.format(
            java.util.Locale.ENGLISH,
            "The array element "
                + "immediately following the end of the collection should be nulled",
            array[1]);
    // the above example was taken from hibernate-orm and was modified a bit
  }

  /** Some javadoc. */
  public void foo22() {
    int[] array = new int[5];

    java.util.List<String> expected = new java.util.ArrayList<>();
    for (int i = 0; i < 5; i++) {
      org.junit.Assert.assertEquals(expected.get(i), array[i]);
    }

    String s =
        String.format(
            java.util.Locale.ENGLISH,
            "The array element "
                + "immediately following the end of the collection should be nulled",
            array[1]);
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo23() {
    new Object();
    // comment
  }

  /** Some javadoc. */
  public void foo24() {

    new Object();
    // odd indentation comment
  }

  /** Some javadoc. */
  public String foo25() {
    return String.format(java.util.Locale.ENGLISH, "%d", 1);
    // comment
  }

  /** Some javadoc. */
  public String foo26() {

    return String.format(java.util.Locale.ENGLISH, "%d", 1);
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo27() {
    // comment
    // block
    foo17();

    // OOOO
  }

  /** Some javadoc. */
  public String foo28() {
    int a = 5;
    return String.format(java.util.Locale.ENGLISH, "%d", 1);
    // comment
  }

  /** Some javadoc. */
  public String foo29() {
    int a = 5;

    return String.format(java.util.Locale.ENGLISH, "%d", 1);
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo30() {
    // comment

    int a = 5;
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo31() {
    String s = new String("A" + "B" + "C");
    // comment
  }

  /** Some javadoc. */
  public void foo32() {

    String s = new String("A" + "B" + "C");
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo33() {
    // comment

    this.foo22();
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo34() throws Exception {
    throw new Exception("", new Exception());
    // comment
  }

  /** Some javadoc. */
  public void foo35() throws Exception {

    throw new Exception("", new Exception());
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo36() throws Exception {

    throw new Exception("", new Exception());
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo37() throws Exception {
    throw new Exception("", new Exception());
    // comment
  }

  /** Some javadoc. */
  public void foo38() throws Exception {

    throw new Exception("", new Exception());
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo39() throws Exception {

    throw new Exception("", new Exception());
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo40() throws Exception {
    int a = 88;

    throw new Exception("", new Exception());
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo41() throws Exception {
    int a = 88;
    throw new Exception("", new Exception());
    // comment
  }

  /** Some javadoc. */
  public void foo42() {
    int a = 5;
    if (a == 5) {
      int b;
      // comment
    } else if (a == 6) {
      /* foo */
    }
  }

  /** Some javadoc. */
  public void foo43() {
    try {
      int a;
      // comment
    } catch (Exception e) {
      /* foo */
    }
  }

  /** Some javadoc. */
  public void foo44() {
    int ar = 5;
    // comment
    ar = 6;
    // comment
  }

  /** Some javadoc. */
  public void foo45() {
    int ar = 5;
    // comment

    ar = 6;
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo46() {

    // comment
    // block
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo47() {
    int a = 5;
    // comment
    // block
    // comment
  }

  /** Some javadoc. */
  public void foo48() {

    int a = 5;
    // comment
    // block
    // odd indentation comment
  }

  /** Some javadoc. */
  public void foo49() {
    // comment
    // block
    // ok
  }

  /** Some javadoc. */
  public void foo50() {
    return;

    // No NPE here!
  }

  /** Some javadoc. */
  public String foo51() {

    return String.valueOf("11");
    // odd indentation comment
  }

  /** Some javadoc. */
  public String foo52() {
    return String.valueOf("11");
    // comment
  }

  // We almost reached the end of the class here.
}
// The END of the class.
