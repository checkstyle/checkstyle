/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodParamPad"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.whitespace.methodparampad;

class InputMethodParamPadFlexibleConstructorBodies {

    static class Before {
        Before (boolean stop) { // violation ''(' is preceded with whitespace.'
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
