/*
SingleSpaceSeparator
validateComments = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.singlespaceseparator;

public class InputSingleSpaceSeparatorForElse {
      void testIf(int x) {
        // violation below 'Use a single space to separate non-whitespace characters'
        if   (x > 0) {
          System.out.println("Positive");
          // violation 2 lines below 'Use a single space to separate non-whitespace characters'
          // violation below 'Use a single space to separate non-whitespace characters'
        }       else       {
          return;
        }
      }

      void testFor() {
        // violation below 'Use a single space to separate non-whitespace characters'
        for    (int i = 0; i < 5; i++) {
          System.out.println(i);
        }
      }
}
