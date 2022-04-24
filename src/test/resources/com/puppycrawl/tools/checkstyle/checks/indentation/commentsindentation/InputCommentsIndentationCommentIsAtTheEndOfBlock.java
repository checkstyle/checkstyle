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
                         // violation
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

     /////////////////////////////// violation (a single-line border to separate a group of methods)

    public void foo7() {
        int a = 0;
// violation
    }

    /////////////////////////////// (a single-line border to separate a group of methods)

    public void foo8() {}

    public class TestClass {
        public void test() {
            int a = 0;
               // violation
        }
          // violation
    }

    public void foo9() {
        this.foo1();
             // violation
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
                  // violation
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
                               // violation
    }

    public void foo15() {
        String
              .valueOf(new Integer(0));
        // comment
    }

    public void foo16() {
        String
            .valueOf(new Integer(0));
                     // violation
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
                             // violation
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
                          // violation
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
                                 // violation
    }

    public void foo23() {
        new Object();
        // comment
    }

    public void foo24() {
        new Object();
                     // violation
    }

    public String foo25() {
        return String.format(java.util.Locale.ENGLISH, "%d",
            1);
        // comment
    }

    public String foo26() {
        return String.format(java.util.Locale.ENGLISH, "%d",
            1);
                                  // violation
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
                          // violation
    }

    public void foo30() {
        // comment
        int a = 5;
//        violation
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
            // violation
    }

    public void foo33() {
        // comment
        this.foo22();
//        violation
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
            // violation
    }

    public void foo36() throws Exception {
        throw new Exception("",
            new Exception()
        );
// violation
    }

    public void foo37() throws Exception {
        throw new Exception("", new Exception());
        // comment
    }

    public void foo38() throws Exception {
        throw new Exception("", new Exception());
              // violation
    }

    public void foo39() throws Exception {
        throw new Exception("",
            new Exception());
         // violation
    }

    public void foo40() throws Exception {
        int a = 88;
        throw new Exception("", new Exception());
         // violation
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
         // violation
    }

    public void foo46() {
// comment
// block
// violation
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
// violation
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
         // violation
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
            /* violation */
        new Object()
            .toString();
            // violation
    }

    void foo56() {
        new Object().toString();
        // comment
    }

    void foo57() {
        new Object().toString();
            // violation
    }

    void foo58() {
        /*
           comment
           */
        // violation
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

//  violation
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

          /* violation */
    }

    void foo67()  {
        try {
            getClass();
        } finally {
            hashCode();
        }

          /* violation */
    }

    void foo68()  {
        for (int i = 0; i < 0; i++) {
            getClass();
        }

          /* violation */
    }

    void foo69()  {
        while (true) {
            getClass();
        }

          /* violation */
    }

    void foo70()  {
        do {
            getClass();
        } while (true);

          /* violation */
    }

    void foo71() {
        switch("") {
            case "!":
                break;
            default:
                break;
        }

          // violation
    }

    void foo72() {
        int u = 1;

/* comment */
// violation
    }

    void foo73() {
        class Foo { }

/* comment */
// violation
    }

    interface Bar1 {
        interface NestedBar { }

// violation
    }

    static class Bar2 {
        enum Foo {
            A;
        }

    // violation
    }

    static class Bar3 {
        @interface Foo { }
            // violation
    }

    void foo74() {
        getClass(); // comment
// comment
// comment
    }

    void foo75() {
        getClass();
// comment
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
//         test
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
            // violation
    }
    void foo79() {
        /* violation */
        /* violation */new Object().toString();
        // ok
    }

    // We almost reached the end of the class here.
}
// The END of the class.
