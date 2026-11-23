/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RedundantModifier">
      <property name="jdkVersion" value="21"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

class InputRedundantModifierFlexibleConstructorBodiesCustom {

    static class Before {
        public Before(boolean stop) { // violation 'Redundant 'public' modifier.'
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
