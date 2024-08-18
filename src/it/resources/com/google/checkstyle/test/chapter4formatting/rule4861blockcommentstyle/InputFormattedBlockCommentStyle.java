package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;

import java.util.Arrays;

/** Testing. */
public class InputFormattedBlockCommentStyle {

  public void foo1() {
    foo2();
    // OOOO: missing functionality
  }

  /** some javadoc. */
  public void foo2() {

    foo3();
    // odd indentation comment
  }

  public void foo3() {
    foo2();
    // refreshDisplay();
  }

  public void foo4() {
    foooooooooooooooooooooooooooooooooooooooooo();
    // ^-- some hint
  }

  public void foooooooooooooooooooooooooooooooooooooooooo() {}

  /////////////////////////////// odd indentation comment

  /** some javadoc. */
  public void foo7() {

    int a = 0;
    // odd indentation comment
  }

  /////////////////////////////// (a single-line border to separate a group of methods)

  public void foo8() {}

  /** some javadoc. */
  public class TestClass {
    /** some javadoc. */
    public void test() {

      int a = 0;
      // odd indentation comment
    }
    // odd indentation comment
  }

  /** some javadoc. */
  public void foo9() {

    this.foo1();
    // odd indentation comment
  }

  //    public void foo10() {
  //
  //    }

  public void foo11() {
    String.valueOf(new Integer(0)).trim().length();
    // comment
  }

  /** some javadoc. */
  public void foo12() {

    String.valueOf(new Integer(0)).trim().length();
    // odd indentation comment
  }

  public void foo13() {
    String.valueOf(new Integer(0)).trim().length();
    // comment
  }

  /** some javadoc. */
  public void foo14() {

    String.valueOf(new Integer(0)).trim().length();
    // odd indentation comment
  }

  public void foo15() {
    String.valueOf(new Integer(0));
    // comment
  }

  /** some javadoc. */
  public void foo16() {

    String.valueOf(new Integer(0));
    // odd indentation comment
  }

  /** some javadoc. */
  public void foo17() {
    String.valueOf(new Integer(0))
        .trim()
        // comment
        .length();
  }

  /** some javadoc. */
  public void foo18() {

    String.valueOf(new Integer(0))
        .trim()
        // odd indentation comment
        .length();
  }

  /** some javadoc. */
  public void foo19() {
    (new Thread(
            new Runnable() {
              @Override
              public void run() {}
            }))
        .run();
    // comment
  }

  /** some javadoc. */
  public void foo20() {

    (new Thread(
            new Runnable() {
              @Override
              public void run() {}
            }))
        .run();
    // odd indentation comment
  }

  /** some javadoc. */
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

  /** some javadoc. */
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

  public void foo23() {
    new Object();
    // comment
  }

  /** some javadoc. */
  public void foo24() {

    new Object();
    // odd indentation comment
  }

  public String foo25() {
    return String.format(java.util.Locale.ENGLISH, "%d", 1);
    // comment
  }

  /** some javadoc. */
  public String foo26() {

    return String.format(java.util.Locale.ENGLISH, "%d", 1);
    // odd indentation comment
  }

  /** some javadoc. */
  public void foo27() {
    // comment
    // block
    foo17();

    // OOOO
  }

  /** some javadoc. */
  public String foo28() {
    int a = 5;
    return String.format(java.util.Locale.ENGLISH, "%d", 1);
    // comment
  }

  /** some javadoc. */
  public String foo29() {
    int a = 5;

    return String.format(java.util.Locale.ENGLISH, "%d", 1);
    // odd indentation comment
  }

  /** some javadoc. */
  public void foo30() {
    // comment

    int a = 5;
    // odd indentation comment
  }

  /** some javadoc. */
  public void foo31() {
    String s = new String("A" + "B" + "C");
    // comment
  }

  /** some javadoc. */
  public void foo32() {

    String s = new String("A" + "B" + "C");
    // odd indentation comment
  }

  /** some javadoc. */
  public void foo33() {
    // comment

    this.foo22();
    // odd indentation comment
  }

  /** some javadoc. */
  public void foo34() throws Exception {
    throw new Exception("", new Exception());
    // comment
  }

  /** some javadoc. */
  public void foo35() throws Exception {

    throw new Exception("", new Exception());
    // odd indentation comment
  }

  /** some javadoc. */
  public void foo36() throws Exception {

    throw new Exception("", new Exception());
    // odd indentation comment
  }

  public void foo37() throws Exception {
    throw new Exception("", new Exception());
    // comment
  }

  /** some javadoc. */
  public void foo38() throws Exception {

    throw new Exception("", new Exception());
    // odd indentation comment
  }

  /** some javadoc. */
  public void foo39() throws Exception {

    throw new Exception("", new Exception());
    // odd indentation comment
  }

  /** some javadoc. */
  public void foo40() throws Exception {
    int a = 88;

    throw new Exception("", new Exception());
    // odd indentation comment
  }

  /** some javadoc. */
  public void foo41() throws Exception {
    int a = 88;
    throw new Exception("", new Exception());
    // comment
  }

  /** some javadoc. */
  public void foo42() {
    int a = 5;
    if (a == 5) {
      int b;
      // comment
    } else if (a == 6) {
      /* foo */
    }
  }

  /** some javadoc. */
  public void foo43() {
    try {
      int a;
      // comment
    } catch (Exception e) {
      /* foo */
    }
  }

  /** some javadoc. */
  public void foo44() {
    int ar = 5;
    // comment
    ar = 6;
    // comment
  }

  /** some javadoc. */
  public void foo45() {
    int ar = 5;
    // comment

    ar = 6;
    // odd indentation comment
  }

  /** some javadoc. */
  public void foo46() {

    // comment
    // block
    // odd indentation comment
  }

  /** some javadoc. */
  public void foo47() {
    int a = 5;
    // comment
    // block
    // comment
  }

  /** some javadoc. */
  public void foo48() {

    int a = 5;
    // comment
    // block
    // odd indentation comment
  }

  /** some javadoc. */
  public void foo49() {
    // comment
    // block
    // ok
  }

  /** some javadoc. */
  public void foo50() {
    return;

    // No NPE here!
  }

  /** some javadoc. */
  public String foo51() {

    return String.valueOf("11");
    // odd indentation comment
  }

  public String foo52() {
    return String.valueOf("11");
    // comment
  }
}

// violation below 'Top-level class ExtraClass1 has to reside in its own source file.'
class ExtraClass1 {

  private void foo1() {
    int a = 5;
    int b = 3;
    int v = 6;
    if (a == b && v == b
        || (a == 1

            /// // odd indentation comment
            //
            /*   // odd indentation comment
             * one fine day ... */
            && b == 1)) {
      // Cannot clearly detect user intention of explanation target.
    }
  }

  private void foo2() {
    int a = 5;
    int b = 3;
    int v = 6;
    if (a == b && v == b || (a == 1 && b == 1)) {

      // comment
    }
  }

  private void foo3() {
    int a = 5;
    int b = 3;
    int v = 6;
    if (a == b && v == b || (a == 1 && b == 1)) {
      // odd indentation comment
    }
  }

  // Comments here should be ok by Check
  @SuppressWarnings("unused") // trailing
  private static void foo4() { // trailing
    if (true) { // trailing comment
      // some comment
    }
    if (true) { // trailing comment
    }
  }

  // Comments here should be ok by Check
  @SuppressWarnings("unused") // trailing
  private static void foo5() { // trailing
    if (true) { // trailing comment

      // odd indentation comment
    }
    if (true) { // trailing comment
    }
  }

  /** some javadoc. */
  public void foo6() {
    try {
      /* foo */
    } catch (Exception e) {
      // odd indentation comment
    }
  }

  /** some javadoc. */
  public void foo7() {
    try {
      /* foo */
    } catch (Exception e) {
      // OOOO: handle exception here
    }
  }

  private static class MyClass extends Object {
    // no members
  }
}

// violation below 'Top-level class ExtraClass2 has to reside in its own source file.'
class ExtraClass2 {

  private static void fooSwitch() {
    switch ("") {
      case "0": // some comment
      case "1":
        // my comment
        foo1();
        break;
      case "2":
        // my comment
        // comment
        foo1();
        // comment
        break;
      case "3":
        /* // odd indentation comment
         *  */

        foo1();
        /* com */
        break;
      case "5":
        foo1();
        // odd indentation comment
        break;
      case "6":
        int k = 7;
        // fall through
      case "7":
        if (true) {
          /* foo */
        }
        // odd indentation comment
        break;
      case "8":
        break;
      case "9":
        foo1();
        // fall through
      case "10":
        // fall through
      case "11":
        // fall through
      case "28":
        // fall through
      case "12":
        break;
      case "13":
        break;
      case "14":
        break;
      case "15":
        break;
      case "16":
        // fall through
      case "17":

        // odd indentation comment
        break;
      case "18":
        break;
      case "19":
        // comment
      case "20":
        // comment
      case "21":
      default:
        // comment
        break;
    }
  }

  private static void foo1() {
    if (true) {
      switch (1) {
        case 0:

        case 1:

          // odd indentation comment
          int b = 10;
          break;
        default:
          // comment
      }
    }
  }

  /** some javadoc. */
  public void fooDotInCaseBlock() {
    int i = 0;
    String s = "";

    switch (i) {
      case -2:
        // what
        i++;
        break;
      case 0:
        // what
        s.indexOf("ignore");
        break;
      case -1:
        // what

        s.indexOf("no way");
        // odd indentation comment
        break;
      case 1:
      case 2:
        i--;
        break;
      case 3:
        // fall through
      default:
    }

    String breaks =
        ""

            // odd indentation comment
            + "</table>"
            // middle
            + ""
        // end
        ;
  }

  /** some javadoc. */
  public void foo2() {
    int a = 1;
    switch (a) {
      case 1:
      default:
        // odd indentation comment
    }
  }

  /** some javadoc. */
  public void foo3() {
    int a = 1;
    switch (a) {
      case 1:
      default:

        // comment
    }
  }

  /** some javadoc. */
  public void foo4() {
    int a = 1;
    switch (a) {
      case 1:
        int b;
        // odd indentation comment
        break;
      default:
    }
  }

  /** some javadoc. */
  public void foo5() {
    int a = 1;
    switch (a) {
      case 1:
        int b;
        // comment
        break;
      default:
    }
  }

  /** some javadoc. */
  public void foo6() {
    int a = 1;
    switch (a) {
      case 1:
        int b;
        // comment
        break;
      default:
    }
  }

  /** some javadoc. */
  public void foo7() {
    int a = 2;
    String s = "";
    switch (a) {
        // comment
        // comment
        // comment
      case 1:
      case 2:
        // comment
        // comment
        foo1();
        // comment
        break;
      case 3:
        // comment
        // comment
        // comment

      case 4:
        // odd indentation comment
      case 5:
        s.toString().toString().toString();
        // odd indentation comment
        // odd indentation comment
        // odd indentation comment
        break;
      default:
    }
  }

  /** some javadoc. */
  public void foo8() {
    int a = 2;
    String s = "";
    switch (a) {
        // comment
        // comment
        // comment
      case 1:
      case 2:
        // comment
        // comment
        foo1();
        // comment
        break;
      case 3:
        // comment
        // comment
        s.toString().toString().toString();
        // comment

        break;
      case 4:
        // odd indentation comment
      default:
    }
  }

  /** some javadoc. */
  public void foo9() {
    int a = 5;
    switch (a) {
      case 1:
      case 2:
        // comment
      default:
    }
  }

  /** some javadoc. */
  public void foo10() {
    int a = 5;
    switch (a) {
      case 1:
      default:
        // comment
    }
  }

  /** some javadoc. */
  public void foo11() {
    int a = 5;
    switch (a) {
      case 1:
      case 2:
        // comment
      default:
    }
  }

  /** some javadoc. */
  public void foo12() {
    int a = 5;
    switch (a) {
        // comment
      case 1:
      default:
    }
  }
}

// violation below 'Top-level class ExtraClass3 has to reside in its own source file.'
class ExtraClass3 {
  private void foo1() {
    if (true) {
      // here initialize some variables
      int k = 0; // trailing comment
      // odd indentation comment

      int b = 10;
      // sss
    }
  }

  private void foo2() {
    if (true) {
      /* some */
      int k = 0;
      /* // odd indentation comment
       */

      int b = 10;
      /* // odd indentation comment
       * */

      double d; /* trailing comment */
      /* // odd indentation comment
       *
       */

      boolean bb;
      /* my comment*/
      /*
       *
       *
       *  some
       */
      /*
       * comment
       */
      boolean x;
    }
  }

  private void foo3() {
    int a = 5;
    int b = 3;
    int v = 6;
    if (a == b && v == b
        || (a == 1
            /// // odd indentation comment
            /* // odd indentation comment
             * one fine day ... */

            && b == 1)) {
      /* foo */
    }
  }

  private static void com() {
    /* here's my weird trailing comment */ boolean b = true;
  }

  private static final String[][] mergeMatrix = {
    // This example of trailing block comments was found in PMD sources.
    /* TOP */ {
      "",
    },
    /* ALWAYS */ {
      "", "",
    },
    /* NEVER */ {
      "NEVER", "UNKNOWN", "NEVER",
    },
    /* UNKNOWN */ {"UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN"},
  };

  private void foo4() {
    if (!Arrays.equals(new String[] {""}, new String[] {""}) /* wierd comment */) {
      /* foo */
    }
  }

  /** some javadoc. */
  private static void testing() {}

  /** some javadoc. */
  public void foid5() {
    String s = "";
    s.toString().toString().toString();
    // comment
  }

  /** some javadoc. */
  public void foo6() {
    // comment
    // ...
    // block
    // ...
    // odd indentation comment

    String someStr = new String();
  }

  /** some javadoc. */
  public void foo7() {
    // comment
    // ...
    // block
    // odd indentation comment
    // comment

    String someStr = new String();
  }

  /** some javadoc. */
  public void foo8() {
    String s = new String(); // comment
    // ...
    // block
    // ...
    // odd indentation comment

    String someStr = new String();
  }

  public String foo9(String s1, String s2, String s3) {
    return "";
  }

  /** some javadoc. */
  public void foo10() throws Exception {

    final String pattern = "^foo$";

    final String[] expected = {
      "7:13: " + foo9("", "", ""),
      // comment
    };
  }
}
