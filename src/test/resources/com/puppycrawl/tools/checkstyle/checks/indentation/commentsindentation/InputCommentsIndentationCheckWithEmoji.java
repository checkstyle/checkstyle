package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationCheckWithEmoji {

    public void myMethod() {
        String breaks = "J"
        // warn 'Indentation should be the same level as line 8'
                + "🥳"
                // it is OK
                + "🥳VASd🥳"
                + "A" + "🥳"
                // it is OK
                ;
    }

    public void test() {
        String a = "🥳";
            // warn 'Indentation should be the same level as line 17'
    }
        // warn 'Indentation should be the same level as line 22'

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
                // comment
            default: a = "🎄".
                        toString();
                // warn 'Indentation should be the same level as line 38'
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
            // warn 'Indentation should be the same level as line 65'

        try {
            assert a.equals("🎄") == true;
        // warn 'Indentation should be the same level as line 66'
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
            // warn 'Indentation should be the same level as line 83'
        // comment
        String someStr = "🎄🎄😅";
    }

    private void test6() {
        if (true) {
            /* some */
            String k = "🎄🎄😅";
                /* warn 'Indentation should be the same level as line 91' */
            int b = Integer.parseInt("🎄🎄😅");
                /* warn 'Indentation should be the same level as line 94'
                * */
            double d; /* trailing comment */
                /* warn 'Indentation should be the same level as line 108'
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

