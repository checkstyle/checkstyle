/*
SingleSpaceSeparator
validateComments = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.singlespaceseparator;

public class InputSingleSpaceSeparatorWithEmoji {

    String s1 = "🧐😉           assa  ";
    String s2 = "🧐😉" + "          " + "assa  ";
    String s3 =  "🧐" + "🎄 "; // violation 'Use a single space to separate non-whitespace characters'
    String s4
            =   "da🎄"; // violation 'Use a single space to separate non-whitespace characters'
    private void foo(String s) {
      if (s.equals("🤩🎄")  ){ // violation 'Use a single space to separate non-whitespace characters'
                foo(s);
      }
    }

    /* ok */ /*🧐*/ String s = "🎄😉";
    String /* 🧐  block comment ok */   st =  "🎄  assa";
    // violation above 'Use a single space to separate non-whitespace characters'
    String str = "🤩🎄";   // Multiple whitespaces before comment
;       String j = ""; // violation 'Use a single space to separate non-whitespace characters'
    /**
     * ok
     */
    void foo2() {
        /* ok */
        String  s = "🧐  🧐"; // violation 'Use a single space to separate non-whitespace characters'
    }

    private  void foo3  (String     s) { // 3 violations
        if (s.substring(0).equals("da🎄")  )  { // 2 violations
          if  ("🧐". // violation 'Use a single space to separate non-whitespace characters'
                  isEmpty()){
/*🧐*/       }  else { // violation 'Use a single space to separate non-whitespace characters'
          }
        }

    }
}
