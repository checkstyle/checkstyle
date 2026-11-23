/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="GoogleRightCurly"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

class InputGoogleRightCurlyFlexibleConstructorBodies {

    static class Before {
        Before(boolean stop) {
            // violation 5 lines below """'}' at column 13 should be on the same line
            // as the next part of a multi-block statement (one that directly
            // contains multiple blocks: if/else-if/else, do/while or try/catch/finally)."""
            if (stop) {
                return;
            }
            else {
                System.out.println("go");
            }
            super();
        }
    }

    static class After {
        After(boolean stop) {
            super();
            int local = 1; }
        // violation above ''}' at column 28 should be alone on a line.'
    }

    static class Clean {
        Clean(boolean stop) {
            int local = 1;
            super();
            if (stop) {
                return;
            } else {
                System.out.println("go");
            }
        }
    }
}
