/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalParameters">
      <property name="ignorePrimitiveTypes" value="true"/>
      <property name="ignoreUnnamedParameters" value="true"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

class InputFinalParametersFlexibleConstructorBodiesCustom {

    static class Before {
        Before(String text, int value) { // violation 'Parameter text should be final.'
            int local = 1;
            super();
            if (text != null) {
                return;
            }
        }
    }

    static class After {
        After(String name, boolean flag) { // violation 'Parameter name should be final.'
            super();
            if (name != null) {
                return;
            }
        }
    }
}
