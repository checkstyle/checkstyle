/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LeftCurly"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

class InputLeftCurlyFlexibleConstructorBodies {

    static class Before {
        Before(boolean stop)
        { // violation ''{' at column 9 should be on the previous line.'
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        After(boolean stop) {
            super();
            int local = 1;
            if (stop) {
                return;
            }
        }
    }
}
