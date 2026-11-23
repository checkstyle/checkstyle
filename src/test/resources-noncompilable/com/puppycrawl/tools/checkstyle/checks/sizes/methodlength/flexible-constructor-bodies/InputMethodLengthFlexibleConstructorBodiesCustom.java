/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodLength">
      <property name="max" value="10"/>
      <property name="countEmpty" value="false"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.sizes.methodlength;

class InputMethodLengthFlexibleConstructorBodiesCustom {

    static class Before {
        // violation below 'Method Before length is 13 lines (max allowed is 10).'
        Before(boolean stop) {
            int value = 0;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            super();
            if (stop) {
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
    }
}
