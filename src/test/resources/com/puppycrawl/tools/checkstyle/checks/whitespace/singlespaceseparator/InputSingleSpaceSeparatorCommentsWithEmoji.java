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
        if (s.equals("🤩🎄") ){ // ok
            foo(s);
        }
    }

    /* ok */ /*🧐*/ String s = "🎄😉";
    String /* 🧐  ok  */  st = "🎄  assa"; // 2 violations
    String str = "🤩🎄";   // violation 'Use a single space to separate non-whitespace characters'
    ;       String j = ""; // violation 'Use a single space to separate non-whitespace characters'
    /**
     * ok
     */
    void foo2() {
        /*🧐 🧐 🧐 🧐*/ /* ok */
        String s = "🧐  🧐";
    }  // violation 'Use a single space to separate non-whitespace characters'

    private void foo3 (String s) {  // ok
        // violation above 'Use a single space to separate non-whitespace characters'
        if (s.substring(0).equals("da🎄")) {  // ok
            // violation above 'Use a single space to separate non-whitespace characters'
            /*🧐 🧐 🧐*/  /* comment */ if ("🧐".isEmpty()){ // ok
            //  violation above 'Use a single space to separate non-whitespace characters'
            }
        }

    }
}
