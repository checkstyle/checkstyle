/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceBeforeEmptyBody"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

class InputWhitespaceBeforeEmptyBodyFlexibleConstructorBodies {

    static class Before {
        Before(boolean stop) {
            int local = 1;
            super();
            if (stop){} // violation ''{' is not preceded with whitespace.'
            if (local > 0) {
                return;
            }
        }
    }

    static class After {
        After(boolean stop) {
            super();
            if (stop) {
                return;
            }
        }

        void method(){} // violation ''{' is not preceded with whitespace.'
    }
}
