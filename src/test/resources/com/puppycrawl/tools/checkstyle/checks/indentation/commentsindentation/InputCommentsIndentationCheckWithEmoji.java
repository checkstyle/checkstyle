package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationCheckWithEmoji {

    public void myMethod() {
        String breaks = "J"
                // violation
                + "🥳"
                // it is OK
                + "🥳VASd🥳"
                + "A" + "🥳"
                // it is OK
                ;
    }

    public void test() {
        String a = "🥳";
            // warn
    }
        // warn

    String s = String.format(java.util.Locale.ENGLISH, " 🥳 🥳 🥳asdda   🥳"
                    + "🎄" + "🎄  🎄🎄       ",
            " ");
    // OK

    public void test2() {
        String a = "🥳";
        switch (a) {
            // comment
            case "1":
                break;
                // comment
            case "2":
                    // comment
                // warn
            default: a = "🎄".
                        toString();
                // warn
        }
    }

    private void test3() { // trailing
        if ("🎄".equals("🎄")) // trailing comment
        {
            // some comment
        }
        if ("🎄".equals("🎄sad")) { // trailing comment

        }
        /**
         *
         */
    }

    private void test4() {
        String a = "🎄";
        a.toString()
                // comment
                .toLowerCase()
                // comment
                .charAt(0);
        // warn

        try {
            assert a.equals("🎄") == true;
        // violation
        }
        catch (Exception ex) {

        } // ok
        finally {

        }
    }

    public void test5() {
            // comment
            // ...
            // block
            // violation
        // comment
        String someStr = "🎄🎄😅";
    }

    private void test6() {
        if (true) {
            /* some */
            String k = "🎄🎄😅";
                /* violation */
            int b = Integer.parseInt("🎄🎄😅");
                /* violation
                * */
            double d; /* trailing comment */
                /* violation
             *
                */

            /* my comment*/
            /*
             *
             *
             *  some
             */
            /*
             * comment
             */
            String x = "😁mkuhyg";
        }
    }
}

