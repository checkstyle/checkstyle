/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;




/**
 * Contains examples of using comments at the end of the block.
 */
public class InputCommentsIndentationCommentIsAtTheEndOfBlock {

    public void foo1() {
        foo2();
        // OOOO: missing functionality
    }

    public void foo2() {
        foo3();
                         // violation '.* incorrect .* level 25, expected is 8,.*same.*as line 24.'
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
    // violation below '.* incorrect .* level 5, expected is 4, .* same .* as line 42.'
     /////////////////////////////// (a single-line border to separate a group of methods)

    public void foo7() {
        int a = 0;
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 43.'
    }

    /////////////////////////////// (a single-line border to separate a group of methods)

    public void foo8() {}

    public class TestClass {
        public void test() {
            int a = 0;
               // violation '.* incorrect .* level 15, expected is 12, .* same .* as line 53.'
        }
          // violation '.* incorrect .* level 10, expected is 8, .* same .* as line 52.'
    }

    public void foo9() {
        this.foo1();
             // violation '.* incorrect .* level 13, expected is 8, .* same .* as line 60.'
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
        String
            .valueOf(new Integer(0))
            .trim()
            .length();
                  // violation '.* incorrect .* level 18, expected is 8, .* same .* as line 77.'
    }

    public void foo13() {
        String.valueOf(new Integer(0))
                .trim()
                .length();
        // comment
    }

    public void foo14() {
        String.valueOf(new Integer(0))
            .trim()
            .length();
                               // violation '.* incorrect .* level 31, expected is 8,.*as line 92.'
    }

    public void foo15() {
        String
              .valueOf(new Integer(0));
        // comment
    }

    public void foo16() {
        String
            .valueOf(new Integer(0));
                     // violation '.* incorrect .* level 21, expected is 8,.* same .* as line 105.'
    }

    public void foo17() {
        String
            .valueOf(new Integer(0))
            .trim()
            // comment
            .length();
    }

    public void foo18() {
        String
            .valueOf(new Integer(0))
            .trim()
                             // violation '.* incorrect .* level 29, expected is 12,.*as line 123.'
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
        (new Thread(new Runnable() {
            @Override
            public void run() {

            }
        })).
            run();
                          // violation '.* incorrect .* level 26, expected is 8, .* as line 138.'
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
        String s = String.format(java.util.Locale.ENGLISH, "The array element "
                + "immediately following the end of the collection should be nulled",
            array[1]);
                                 // violation '.*incorrect.*level 33, expected is 8,.*as line 168.'
    }

    public void foo23() {
        new Object();
        // comment
    }

    public void foo24() {
        new Object();
                     // violation '.* incorrect .* level 21, expected is 8,.* same .* as line 180.'
    }

    public String foo25() {
        return String.format(java.util.Locale.ENGLISH, "%d",
            1);
        // comment
    }

    public String foo26() {
        return String.format(java.util.Locale.ENGLISH, "%d",
            1);
                                  // violation '.*incorrect.*level 34, expected is 8,.*as line 191.'
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
        return String.format(java.util.Locale.ENGLISH, "%d",
            1);
                          // violation '.* incorrect .* level 26, expected is 8, .* as line 213.'
    }

    public void foo30() {
        // comment
        int a = 5;
//        violation '.* incorrect .* level 0, expected is 8, .* same .* as line 220.'
    }

    public void foo31() {
        String s = new String ("A"
            + "B"
            + "C");
        // comment
    }

    public void foo32() {
        String s = new String ("A"
            + "B"
            + "C");
            // violation '.* incorrect .* level 12, expected is 8, .* same .* as line 232.'
    }

    public void foo33() {
        // comment
        this.foo22();
//        violation '.* incorrect .* level 0, expected is 8, .* same .* as line 240.'
    }

    public void foo34() throws Exception {
        throw new Exception("",
            new Exception()
            );
        // comment
    }

    public void foo35() throws Exception {
        throw new Exception("",
            new Exception()
        );
            // violation '.* incorrect .* level 12, expected is 8, .* same .* as line 252.'
    }

    public void foo36() throws Exception {
        throw new Exception("",
            new Exception()
        );
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 259.'
    }

    public void foo37() throws Exception {
        throw new Exception("", new Exception());
        // comment
    }

    public void foo38() throws Exception {
        throw new Exception("", new Exception());
              // violation '.* incorrect .* level 14, expected is 8, .* same .* as line 271.'
    }

    public void foo39() throws Exception {
        throw new Exception("",
            new Exception());
         // violation '.* incorrect .* level 9, expected is 8, .* same .* as line 276.'
    }

    public void foo40() throws Exception {
        int a = 88;
        throw new Exception("", new Exception());
         // violation '.* incorrect .* level 9, expected is 8, .* same .* as line 283.'
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
        ar = 6;
         // violation '.* incorrect .* level 9, expected is 8, .* same .* as line 322.'
    }

    public void foo46() {
// comment
// block
// violation '.* incorrect .* level 0, expected is 4, .* same .* as line 330.'
    }

    public void foo47() {
        int a = 5;
        // comment
        // block
        // comment
    }

    public void foo48() {
        int a = 5;
// comment
// block
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 340.'
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
        return String
            .valueOf("11"
            );
         // violation '.* incorrect .* level 9, expected is 8, .* same .* as line 359.'
    }

    public String foo52() {
        return String
            .valueOf("11"
            );
        // comment
    }

    void foo53() {
        // comment
        new Object()
            .toString();
        // comment
    }

    void foo54() {
        /* comment */
        new Object()
            .toString();
        // comment
    }

    void foo55() {
        // violation below '.* incorrect .* level 12, expected is 8, .* same .* as line 389.'
            /* violation */
        new Object()
            .toString();
            // violation '.* incorrect .* level 12, expected is 8, .* same .* as line 389.'
    }

    void foo56() {
        new Object().toString();
        // comment
    }

    void foo57() {
        new Object().toString();
            // violation '.* incorrect .* level 12, expected is 8, .* same .* as line 400.'
    }

    void foo58() {
        /*
           comment
           */
        // violation '.* incorrect .* level 8, expected is 10, .* same .* as line 409.'
          foo1();
          // comment
    }

    void foo59() {
        foo1();
        /*
         comment */
        // comment
    }


    void foo61() {
        foo1();
        /*
         * comment
         */
        /*
         * comment
         */
    }

    void foo62() {
        if (true) {
            String.CASE_INSENSITIVE_ORDER.equals("");
          }
        else {

          }
        /*
         comment
         */
        /*
         comment
         */
    }

    void foo63() {
        try {
            String.CASE_INSENSITIVE_ORDER.equals("");
          }
        catch (Exception e){

          }

        /*
         comment
         */
        /*
         comment
         */
    }

    void foo64()  {
        foo1();

//  violation '.* incorrect .* level 0, expected is 8, .* same .* as line 463.'
    }

    void foo65() {
        int i = 1
            + 1
            + 1;
        // comment
        // comment
    }

    void foo66()  {
        if (true) {
            getClass();
        }
        // violation below '.* incorrect .* level 10, expected is 8, .* same .* as line 477.'
          /* violation */
    }

    void foo67()  {
        try {
            getClass();
        } finally {
            hashCode();
        }
        // violation below '.* incorrect .* level 10, expected is 8, .* same .* as line 485.'
          /* violation */
    }

    void foo68()  {
        for (int i = 0; i < 0; i++) {
            getClass();
        }
        // violation below '.* incorrect .* level 10, expected is 8, .* same .* as line 495.'
          /* violation */
    }

    void foo69()  {
        while (true) {
            getClass();
        }
        // violation below '.* incorrect .* level 10, expected is 8, .* same .* as line 503.'
          /* violation */
    }

    void foo70()  {
        do {
            getClass();
        } while (true);
        // violation below '.* incorrect .* level 10, expected is 8, .* same .* as line 511.'
          /* violation */
    }

    void foo71() {
        switch("") {
            case "!":
                break;
            default:
                break;
        }

          // violation '.* incorrect .* level 10, expected is 8, .* same .* as line 519.'
    }

    void foo72() {
        int u = 1;

/* comment */
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 530.'
    }

    void foo73() {
        class Foo { }

/* comment */
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 537.'
    }

    interface Bar1 {
        interface NestedBar { }

// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 544.'
    }

    static class Bar2 {
        enum Foo {
            A;
        }

    // violation '.* incorrect .* level 4, expected is 8, .* same .* as line 550.'
    }

    static class Bar3 {
        @interface Foo { }
            // violation '.* incorrect .* level 12, expected is 8, .* same .* as line 558.'
    }

    void foo74() {
        getClass(); // comment
// comment
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 563.'
    }

    void foo75() {
        getClass();
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 569.'
        // comment
    }

    void InputCommentsIndentationCommentIsAtTheEndOfBlock(String s) {
        assert(s == null ||
                s != null);
         // comment
         //comment
    }

    int foo76() {
        return 0;
        /* test
* test */
//         violation '.* incorrect .* level 0, expected is 8, .* same .* as line 582.'
    }

    void foo77() {
        try {
        /* CHECKSTYLE:OFF */} catch(Exception e) {
        }
    }
    void foo78() {
        /* violation */
        new Object()
            .toString();
            // violation '.* incorrect .* level 12, expected is 8, .* same .* as line 595.'
    }
    void foo79() {
        /* violation */
        /* violation */new Object().toString();
        // ok
    }

    // We almost reached the end of the class here.
}
// The END of the class.
