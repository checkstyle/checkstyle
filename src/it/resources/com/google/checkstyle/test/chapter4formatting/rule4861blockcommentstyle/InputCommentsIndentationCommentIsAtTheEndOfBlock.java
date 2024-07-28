package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;

/** Contains examples of using comments at the end of the block. */
public class InputCommentsIndentationCommentIsAtTheEndOfBlock {

  public void foo1() {
    foo2();
    // OOOO: missing functionality
  }

  /** some javadoc. */
  public void foo2() {
    // violation 2 lines below '.* indentation should be the same level as line 14.'
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
  // violation above '.* indentation should be the same level as line 34.'

  /** some javadoc. */
  public void foo7() {
    // violation 2 lines below'.* indentation should be the same level as line 36.'
    int a = 0;
// odd indentation comment
  }

  /////////////////////////////// (a single-line border to separate a group of methods)

  public void foo8() {}

  /** some javadoc. */
  public class TestClass {
    /** some javadoc. */
    public void test() {
      // violation 3 lines below '.* indentation should be the same level as line 50.'
      // violation 4 lines below'.* indentation should be the same level as line 47.'
      int a = 0;
         // odd indentation comment
    }
      // odd indentation comment
  }

  /** some javadoc. */
  public void foo9() {
    // violation 2 lines below '.* indentation should be the same level as line 59.'
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
    // violation 5 lines below '.* indentation should be the same level as line 75.'
    String
        .valueOf(new Integer(0))
        .trim()
        .length();
              // odd indentation comment
  }

  public void foo13() {
    String.valueOf(new Integer(0)).trim().length();
    // comment
  }

  /** some javadoc. */
  public void foo14() {
    // violation 4 lines below '.* indentation should be the same level as line 90.'
    String.valueOf(new Integer(0))
        .trim()
        .length();
                           // odd indentation comment
  }

  public void foo15() {
    String.valueOf(new Integer(0));
    // comment
  }

  /** some javadoc. */
  public void foo16() {
    // violation 3 lines below '.* indentation should be the same level as line 104.'
    String
        .valueOf(new Integer(0));
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
    // violation 4 lines below '.* indentation should be the same level as line 124.'
    String
        .valueOf(new Integer(0))
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
    // violation 8 lines below '.* indentation should be the same level as line 141.'
    (new Thread(new Runnable() {
        @Override
        public void run() {

        }
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
    // violation 4 lines below '.* indentation should be the same level as line 177.'
    String s = String.format(java.util.Locale.ENGLISH, "The array element "
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
    // violation 2 lines below '.* indentation should be the same level as line 191.'
    new Object();
                 // odd indentation comment
  }

  public String foo25() {
    return String.format(java.util.Locale.ENGLISH, "%d", 1);
    // comment
  }

  /** some javadoc. */
  public String foo26() {
    // violation 3 lines below '.* indentation should be the same level as line 203.'
    return String.format(java.util.Locale.ENGLISH, "%d",
        1);
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
    // violation 3 lines below '.* indentation should be the same level as line 228.'
    return String.format(java.util.Locale.ENGLISH, "%d",
        1);
                      // odd indentation comment
  }

  /** some javadoc. */
  public void foo30() {
    // comment
    // violation 2 lines below '.* indentation should be the same level as line 237.'
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
    // violation 4 lines below '.* indentation should be the same level as line 250.'
    String s = new String("A"
        + "B"
        + "C");
        // odd indentation comment
  }

  /** some javadoc. */
  public void foo33() {
    // comment
    // violation 2 lines below '.* indentation should be the same level as line 260.'
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
    // violation 4 lines below '.* indentation should be the same level as line 273.'
    throw new Exception("",
        new Exception()
    );
        // odd indentation comment
  }

  /** some javadoc. */
  public void foo36() throws Exception {
    // violation 4 lines below '.* indentation should be the same level as line 282.'
    throw new Exception("",
        new Exception()
    );
// odd indentation comment
  }

  public void foo37() throws Exception {
    throw new Exception("", new Exception());
    // comment
  }

  /** some javadoc. */
  public void foo38() throws Exception {
    // violation 2 lines below '.* indentation should be the same level as line 296.'
    throw new Exception("", new Exception());
          // odd indentation comment
  }

  /** some javadoc. */
  public void foo39() throws Exception {
    // violation 3 lines below'.* indentation should be the same level as line 303.'
    throw new Exception("",
        new Exception());
     // odd indentation comment
  }

  /** some javadoc. */
  public void foo40() throws Exception {
    int a = 88;
    // violation 2 lines below '.* indentation should be the same level as line 312.'
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
    // violation 2 lines below '.* indentation should be the same level as line 357.'
    ar = 6;
     // odd indentation comment
  }

  /** some javadoc. */
  public void foo46() {
    // violation 3 lines below '.* indentation should be the same level as line 367.'
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
    // violation 4 lines below '.* indentation should be the same level as line 380.'
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
    // violation 4 lines below '.* indentation should be the same level as line 403.'
    return String
        .valueOf("11"
        );
     // odd indentation comment
  }

  public String foo52() {
    return String.valueOf("11");
    // comment
  }

  // We almost reached the end of the class here.
}
// The END of the class.
