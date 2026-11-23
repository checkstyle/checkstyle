/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParenPad">
      <property name="option" value="space"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

class InputParenPadFlexibleConstructorBodiesCustom {

    static class Before {
        Before(boolean stop) { // violation ''(' is not followed by whitespace.'
            // violation above '')' is not preceded with whitespace.'
            int local = 1;
            super();
            if ( stop ) {
                return;
            }
        }
    }

    static class After {
        After( boolean stop ) {
            super();
            int local = 1;
            if ( stop ) {
                return;
            }
        }
    }
}
