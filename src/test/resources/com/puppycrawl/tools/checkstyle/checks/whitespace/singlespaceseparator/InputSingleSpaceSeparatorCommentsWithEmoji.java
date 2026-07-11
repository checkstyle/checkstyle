/*
SingleSpaceSeparator
validateComments = true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.singlespaceseparator;

public class InputSingleSpaceSeparatorCommentsWithEmoji {


    String s1 = "🧐😉           assa  ";
    String s2 = "🧐😉" + "          " + "assa  ";
    String s3 = "🧐" + "🎄 ";
    String s4
        = "da🎄";
    private void foo(String s) {
        if (s.equals("🤩🎄") ){
            foo(s);
        }
    }

    /* ok */ /*🧐*/ String s = "🎄😉";
    String /* 🧐  ok  */  st = "🎄  assa";
    // 2 violations above:
    //     'Use a single space to separate non-whitespace characters.'
    //     'Use a single space to separate non-whitespace characters.'
    String str = "🤩🎄";   // violation 'Use a single space to separate non-whitespace characters'
    ;       String j = ""; // violation 'Use a single space to separate non-whitespace characters'
    /**
     * ok
     */
    void foo2() {
        /*🧐 🧐 🧐 🧐*/ /* ok */
        String s = "🧐  🧐";
    }  // violation 'Use a single space to separate non-whitespace characters'

    private void foo3 (String s) {  // some comment
        // violation above 'Use a single space to separate non-whitespace characters'
        if (s.substring(0).equals("da🎄")) {  // some comment
            // violation above 'Use a single space to separate non-whitespace characters'
            /*🧐 🧐 🧐*/  /* comment */ if ("🧐".isEmpty()){
            // violation above 'Use a single space to separate non-whitespace characters'
            }
        }

    }
}
