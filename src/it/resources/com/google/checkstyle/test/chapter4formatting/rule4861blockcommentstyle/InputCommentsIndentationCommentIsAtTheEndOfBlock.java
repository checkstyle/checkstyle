package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;

/** Contains examples of using comments at the end of the block. */
public class InputCommentsIndentationCommentIsAtTheEndOfBlock {

  /** Some javadoc. */
  public void foo1() {
    foo2();
    // OOOO: missing functionality
  }

  /** Some javadoc. */
  public void foo2() {
    // violation 2 lines below '.* indentation should be the same level as line 15.'
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
  // violation above '.* indentation should be the same level as line 38.'

  /** Some javadoc. */
  public void foo7() {
    // violation 2 lines below'.* indentation should be the same level as line 40.'
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
      // violation 3 lines below '.* indentation should be the same level as line 55.'
      // violation 4 lines below'.* indentation should be the same level as line 52.'
      int a = 0;
         // odd indentation comment
    }
      // odd indentation comment
  }

  /** Some javadoc. */
  public void foo9() {
    // violation 2 lines below '.* indentation should be the same level as line 64.'
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
    // violation 5 lines below '.* indentation should be the same level as line 81.'
    String
        .valueOf(new Integer(0))
        .trim()
        .length();
              // odd indentation comment
  }

  /** Some javadoc. */
  public void foo13() {
    String.valueOf(new Integer(0)).trim().length();
    // comment
  }

  /** Some javadoc. */
  public void foo14() {
    // violation 4 lines below '.* indentation should be the same level as line 97.'
    String.valueOf(new Integer(0))
        .trim()
        .length();
                           // odd indentation comment
  }

  /** Some javadoc. */
  public void foo15() {
    String.valueOf(new Integer(0));
    // comment
  }

  /** Some javadoc. */
  public void foo16() {
    // violation 3 lines below '.* indentation should be the same level as line 112.'
    String
        .valueOf(new Integer(0));
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
    // violation 4 lines below '.* indentation should be the same level as line 132.'
    String
        .valueOf(new Integer(0))
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
    // violation 8 lines below '.* indentation should be the same level as line 149.'
    (new Thread(new Runnable() {
        @Override
        public void run() {

        }
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
    // violation 4 lines below '.* indentation should be the same level as line 185.'
    String s = String.format(java.util.Locale.ENGLISH, "The array element "
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
    // violation 2 lines below '.* indentation should be the same level as line 200.'
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
    // violation 3 lines below '.* indentation should be the same level as line 213.'
    return String.format(java.util.Locale.ENGLISH, "%d",
        1);
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
    // violation 3 lines below '.* indentation should be the same level as line 238.'
    return String.format(java.util.Locale.ENGLISH, "%d",
        1);
                      // odd indentation comment
  }

  /** Some javadoc. */
  public void foo30() {
    // comment
    // violation 2 lines below '.* indentation should be the same level as line 247.'
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
    // violation 4 lines below '.* indentation should be the same level as line 260.'
    String s = new String("A"
        + "B"
        + "C");
        // odd indentation comment
  }

  /** Some javadoc. */
  public void foo33() {
    // comment
    // violation 2 lines below '.* indentation should be the same level as line 270.'
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
    // violation 4 lines below '.* indentation should be the same level as line 283.'
    throw new Exception("",
        new Exception()
    );
        // odd indentation comment
  }

  /** Some javadoc. */
  public void foo36() throws Exception {
    // violation 4 lines below '.* indentation should be the same level as line 292.'
    throw new Exception("",
        new Exception()
    );
// odd indentation comment
  }

  /** Some javadoc. */
  public void foo37() throws Exception {
    throw new Exception("", new Exception());
    // comment
  }

  /** Some javadoc. */
  public void foo38() throws Exception {
    // violation 2 lines below '.* indentation should be the same level as line 307.'
    throw new Exception("", new Exception());
          // odd indentation comment
  }

  /** Some javadoc. */
  public void foo39() throws Exception {
    // violation 3 lines below'.* indentation should be the same level as line 314.'
    throw new Exception("",
        new Exception());
     // odd indentation comment
  }

  /** Some javadoc. */
  public void foo40() throws Exception {
    int a = 88;
    // violation 2 lines below '.* indentation should be the same level as line 323.'
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
    // violation 2 lines below '.* indentation should be the same level as line 368.'
    ar = 6;
     // odd indentation comment
  }

  /** Some javadoc. */
  public void foo46() {
    // violation 3 lines below '.* indentation should be the same level as line 378.'
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
    // violation 4 lines below '.* indentation should be the same level as line 391.'
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
    // violation 4 lines below '.* indentation should be the same level as line 414.'
    return String
        .valueOf("11"
        );
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
