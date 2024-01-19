/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;




/**
 * Contains examples of using comments at the end of the block.
 */
public class InputCommentsIndentationCommentIsAtTheEndOfBlockTwo {

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
        InputCommentsIndentationCommentIsAtTheEndOfBlockOne i = new InputCommentsIndentationCommentIsAtTheEndOfBlockOne();
        i.foo17();

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


}
