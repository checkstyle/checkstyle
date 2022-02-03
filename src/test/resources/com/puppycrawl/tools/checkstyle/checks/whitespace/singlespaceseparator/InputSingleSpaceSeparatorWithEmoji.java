/*
SingleSpaceSeparator
validateComments = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.singlespaceseparator;

public class InputSingleSpaceSeparatorWithEmoji {

    String s1 = "🧐😉           assa  "; // ok
    String s2 = "🧐😉" + "          " + "assa  "; // ok
    String s3 =  "🧐" + "🎄 "; // violation 'Use a single space to separate non-whitespace characters'
    String s4
            =   "da🎄"; // violation 'Use a single space to separate non-whitespace characters'
    private void foo(String s) {
      if (s.equals("🤩🎄")  ){ // violation 'Use a single space to separate non-whitespace characters'
                foo(s);
      }
    }

    /* always correct */ /*🧐*/ String s = "🎄😉";
    String /* 🧐  wrong if X is enabled */   st =  "🎄  assa";
    // violation above 'Use a single space to separate non-whitespace characters'
    String str = "🤩🎄";   // Multiple whitespaces before comment, Wrong if X is enabled
;       String j = ""; // violation 'Use a single space to separate non-whitespace characters'
    /**
     * Always correct
     */
    void foo2() {
        /* Always correct */
        String  s = "🧐  🧐"; // violation 'Use a single space to separate non-whitespace characters'
    }  // Wrong if X is enabled

    private  void foo3  (String     s) { // 3 violations
        if (s.substring(0).equals("da🎄")  )  { // 2 violations
          if  ("🧐". // violation 'Use a single space to separate non-whitespace characters'
                  isEmpty()){

          }
        }

    }
}
