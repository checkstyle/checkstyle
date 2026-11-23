/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodLength"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.sizes.methodlength;

class InputMethodLengthFlexibleConstructorBodies {

    static class Before {
        Before(boolean stop) {
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
