/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodParamPad">
      <property name="option" value="space"/>
      <property name="allowLineBreaks" value="false"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.whitespace.methodparampad;

class InputMethodParamPadFlexibleConstructorBodiesCustom {

    static class Before {
        Before(boolean stop) { // violation ''(' is not preceded with whitespace.'
            int local = 1;
            super ();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        After (boolean stop) {
            super ();
            int local = 1;
            if (stop) {
                return;
            }
        }
    }
}
