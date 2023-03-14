/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationCheckWithEmoji {

    public void myMethod() {
        String breaks = "J"
        // violation '.* incorrect .* level 8, expected is 16, .* same .* as line 15.'
                + "🥳"
                // it is OK 👍
                + "🥳VASd🥳"
                + "A" + "🥳"
                // it is OK 👍
                ;
    }

    public void test() {
        String a = "🥳";
            // violation '.* incorrect .* level 12, expected is 8, .* same .* as line 24.'
    }
        // violation '.* incorrect .* level 8, expected is 4, .* same .* as line 29.'

    String s = String.format(java.util.Locale.ENGLISH, " 🥳 🥳 🥳asdda   🥳"
                    + "🎄" + "🎄  🎄🎄       ",
            " ");
    // OK 🥳

    public void test2() {
        String a = "🥳";
        switch (a) {
            // 🥳 comment
            case "1":
                break;
                // 🎄 comment 🎄
            case "2":
                    // comment  👈🏻
                // 👈🏻 comment
            default: a = "🎄".
                        toString();
                // violation '.* incorrect .* level 16, expected is 24, .* same .* as line 45.'
        }
    }

    private void test3() { // trailing 👉🏻
        if ("🎄".equals("🎄")) // trailing 👉🏻 comment
        {
            // some comment  😁
        }
        if ("🎄".equals("🎄sad")) { // trailing comment 🤔

        }
        /** 🤔
         *           🤔
         */
    }

    private void test4() {
        String a = "🎄";
        a.toString()
                // comment 👇🏻
                .toLowerCase()
                // comment 👆🏻
                .charAt(0);
            // violation '.* incorrect .* level 12, expected is 8, .* same .* as line 72.'

        try {
            assert a.equals("🎄") == true;
        // violation '.* incorrect .* level 8, expected is 12, .* same .* as line 73.'
        }
        catch (Exception ex) {

        } // ok 👍🏻
        finally {

        }
    }

    public void test5() {
            // comment
            // ... 🧐
            // block
            // violation '.* incorrect .* level 12, expected is 8, .* same .* as line 90.'
        // comment
        String someStr = "🎄🎄😅";
    }

    private void test6() {
        if (true) {
            /* some 👌🏻 */
            String k = "🎄🎄😅";
            // violation below '.* incorrect .* level 16, expected is 12,.* same .* as line 99.'
                /* hello there some comment with emoji 👌 */
            int b = Integer.parseInt("🎄🎄😅");
                /* // violation '.* incorrect .* level 16, expected is 12, .* same .* as line 102.'
                * */
            double d; /* trailing comment */
                /* // violation '.* incorrect .* level 16, expected is 12, .* same .* as line 116.'
             *🎄
                */

            /* my comment*/
            /*
             * 🤛🏻
             *
             *  some            😧
             *  😧 /
            /*
             * comment 🤩
             */
            String x = "😁mkuhyg";
        }
    }
}

