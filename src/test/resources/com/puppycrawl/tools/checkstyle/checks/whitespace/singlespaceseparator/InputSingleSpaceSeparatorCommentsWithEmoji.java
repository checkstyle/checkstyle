/*
SingleSpaceSeparator
validateComments = true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.singlespaceseparator;

public class InputSingleSpaceSeparatorCommentsWithEmoji {


    String s1 = "🧐😉           assa  "; // ok
    String s2 = "🧐😉" + "          " + "assa  "; // ok
    String s3 = "🧐" + "🎄 "; // ok
    String s4
        = "da🎄"; // ok
    private void foo(String s) {
        if (s.equals("🤩🎄") ){
            foo(s);
        }
    }

    /* always correct */ /*🧐*/ String s = "🎄😉";
    String /* 🧐  wrong  */  st = "🎄  assa"; // 2 violations
    String str = "🤩🎄";   // violation 'Use a single space to separate non-whitespace characters'
    ;       String j = ""; // violation 'Use a single space to separate non-whitespace characters'
    /**
     * Always correct
     */
    void foo2() {
        /*🧐 🧐 🧐 🧐*/ /* Always correct */
        String s = "🧐  🧐";
    }  // violation 'Use a single space to separate non-whitespace characters'

    private void foo3 (String s) {  // comment
        // violation above 'Use a single space to separate non-whitespace characters'
        if (s.substring(0).equals("da🎄")) {  // comment
            // violation above 'Use a single space to separate non-whitespace characters'
            /*🧐 🧐 🧐*/  /* wrong */if ("🧐".isEmpty()){
            //  violation above 'Use a single space to separate non-whitespace characters'
            }
        }

    }
}
