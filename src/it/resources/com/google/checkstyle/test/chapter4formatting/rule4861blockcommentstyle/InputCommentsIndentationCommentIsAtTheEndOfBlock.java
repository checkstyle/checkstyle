package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;




/**
 * Contains examples of using comments at the end of the block.
 */
public class InputCommentsIndentationCommentIsAtTheEndOfBlock {

    public void foo1() {
        foo2();
        // OOOO: missing functionality
    }

    public void foo2() {
        // violation 2 lines below '.* indentation should be the same level as line 18.'
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

    public void foooooooooooooooooooooooooooooooooooooooooo() { }

     /////////////////////////////// odd indentation comment

    // violation 2 lines above '.* indentation should be the same level as line 37.'
    public void foo7() {
        // violation 2 lines below'.* indentation should be the same level as line 39.'
        int a = 0;
// odd indentation comment
    }

    /////////////////////////////// (a single-line border to separate a group of methods)

    public void foo8() {}

    public class TestClass {
        public void test() {
            // violation 3 lines below '.* indentation should be the same level as line 51.'
            // violation 4 lines below'.* indentation should be the same level as line 48.'
            int a = 0;
               // odd indentation comment
        }
          // odd indentation comment
    }

    public void foo9() {
        // violation 2 lines below '.* indentation should be the same level as line 59.'
        this.foo1();
             // odd indentation comment
    }

    //    public void foo10() {
    //
    //    }

    public void foo11() {
        String
            .valueOf(new Integer(0))
            .trim()
            .length();
        // comment
    }

    public void foo12() {
        // violation 5 lines below '.* indentation should be the same level as line 77.'
        String
            .valueOf(new Integer(0))
            .trim()
            .length();
                  // odd indentation comment
    }

    public void foo13() {
        String.valueOf(new Integer(0))
                .trim()
                .length();
        // comment
    }

    public void foo14() {
        // violation 4 lines below '.* indentation should be the same level as line 93.'
        String.valueOf(new Integer(0))
            .trim()
            .length();
                               // odd indentation comment
    }

    public void foo15() {
        String
              .valueOf(new Integer(0));
        // comment
    }

    public void foo16() {
        // violation 3 lines below '.* indentation should be the same level as line 107.'
        String
            .valueOf(new Integer(0));
                     // odd indentation comment
    }

    public void foo17() {
        String
            .valueOf(new Integer(0))
            .trim()
            // comment
            .length();
    }

    public void foo18() {
        // violation 4 lines below '.* indentation should be the same level as line 126.'
        String
            .valueOf(new Integer(0))
            .trim()
                             // odd indentation comment
            .length();
    }

    public void foo19() {
        (new Thread(new Runnable() {
            @Override
            public void run() {

            }
        })).
            run();
        // comment
    }

    public void foo20() {
        // violation 8 lines below '.* indentation should be the same level as line 142.'
        (new Thread(new Runnable() {
            @Override
            public void run() {

            }
        })).
            run();
                          // odd indentation comment
    }

    public void foo21() {
        int[] array = new int[5];

        java.util.List<String> expected = new java.util.ArrayList<>();
        for (int i = 0; i < 5; i++) {
        org.junit.Assert.assertEquals(expected.get(i), array[i]);
        }
        String s = String.format(java.util.Locale.ENGLISH, "The array element "
               + "immediately following the end of the collection should be nulled",
            array[1]);
        // the above example was taken from hibernate-orm and was modified a bit
    }

    public void foo22() {
        int[] array = new int[5];

        java.util.List<String> expected = new java.util.ArrayList<>();
        for (int i = 0; i < 5; i++) {
            org.junit.Assert.assertEquals(expected.get(i), array[i]);
        }
        // violation 4 lines below '.* indentation should be the same level as line 173.'
        String s = String.format(java.util.Locale.ENGLISH, "The array element "
                + "immediately following the end of the collection should be nulled",
            array[1]);
                                 // odd indentation comment
    }

    public void foo23() {
        new Object();
        // comment
    }

    public void foo24() {
        // violation 2 lines below '.* indentation should be the same level as line 186.'
        new Object();
                     // odd indentation comment
    }

    public String foo25() {
        return String.format(java.util.Locale.ENGLISH, "%d",
            1);
        // comment
    }

    public String foo26() {
        // violation 3 lines below '.* indentation should be the same level as line 198.'
        return String.format(java.util.Locale.ENGLISH, "%d",
            1);
                                  // odd indentation comment
    }

    public void foo27() {
        // comment
        // block
        foo17();

        // OOOO
    }

    public String foo28() {
        int a = 5;
        return String.format(java.util.Locale.ENGLISH, "%d",
            1);
        // comment
    }

    public String foo29() {
        int a = 5;
        // violation 3 lines below '.* indentation should be the same level as line 221.'
        return String.format(java.util.Locale.ENGLISH, "%d",
            1);
                          // odd indentation comment
    }

    public void foo30() {
        // comment
        // violation 2 lines below '.* indentation should be the same level as line 229.'
        int a = 5;
// odd indentation comment
    }

    public void foo31() {
        String s = new String ("A"
            + "B"
            + "C");
        // comment
    }

    public void foo32() {
        // violation 4 lines below '.* indentation should be the same level as line 242.'
        String s = new String ("A"
            + "B"
            + "C");
            // odd indentation comment
    }

    public void foo33() {
        // comment
        // violation 2 lines below '.* indentation should be the same level as line 251.'
        this.foo22();
// odd indentation comment
    }

    public void foo34() throws Exception {
        throw new Exception("",
            new Exception()
            );
        // comment
    }

    public void foo35() throws Exception {
        // violation 4 lines below '.* indentation should be the same level as line 264.'
        throw new Exception("",
            new Exception()
        );
            // odd indentation comment
    }

    public void foo36() throws Exception {
        // violation 4 lines below '.* indentation should be the same level as line 272.'
        throw new Exception("",
            new Exception()
        );
// odd indentation comment
    }

    public void foo37() throws Exception {
        throw new Exception("", new Exception());
        // comment
    }

    public void foo38() throws Exception {
        // violation 2 lines below '.* indentation should be the same level as line 285.'
        throw new Exception("", new Exception());
              // odd indentation comment
    }

    public void foo39() throws Exception {
        // violation 3 lines below'.* indentation should be the same level as line 291.'
        throw new Exception("",
            new Exception());
         // odd indentation comment
    }

    public void foo40() throws Exception {
        int a = 88;
        // violation 2 lines below '.* indentation should be the same level as line 299.'
        throw new Exception("", new Exception());
         // odd indentation comment
    }

    public void foo41() throws Exception {
        int a = 88;
        throw new Exception("", new Exception());
        // comment
    }

    public void foo42() {
        int a = 5;
        if (a == 5) {
            int b;
            // comment
        } else if (a ==6) {

        }
    }

    public void foo43() {
        try {
            int a;
            // comment
        } catch (Exception e) {

        }
    }

    public void foo44() {
        int ar = 5;
        // comment
        ar = 6;
        // comment
    }

    public void foo45() {
        int ar = 5;
        // comment
        // violation 2 lines below '.* indentation should be the same level as line 339.'
        ar = 6;
         // odd indentation comment
    }

    public void foo46() {
    // violation 3 lines below '.* indentation should be the same level as line 348.'
// comment
// block
// odd indentation comment
    }

    public void foo47() {
        int a = 5;
        // comment
        // block
        // comment
    }

    public void foo48() {
        // violation 4 lines below '.* indentation should be the same level as line 359.'
        int a = 5;
// comment
// block
// odd indentation comment
    }

    public void foo49() {
       // comment
       // block
       // ok
    }

    public void foo50() {
        return;

        // No NPE here!
    }

    public String foo51() {
        // violation 4 lines below '.* indentation should be the same level as line 379.'
        return String
            .valueOf("11"
            );
         // odd indentation comment
    }

    public String foo52() {
        return String
            .valueOf("11"
            );
        // comment
    }

    // We almost reached the end of the class here.
}
// The END of the class.
