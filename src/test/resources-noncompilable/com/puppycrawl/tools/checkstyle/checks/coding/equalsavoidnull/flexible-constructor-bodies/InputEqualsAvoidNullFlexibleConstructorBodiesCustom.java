/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EqualsAvoidNull">
      <property name="ignoreEqualsIgnoreCase" value="true"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

class InputEqualsAvoidNullFlexibleConstructorBodiesCustom {

    static class Before {
        Before(String text) {
            boolean result = text.equalsIgnoreCase("value");
            boolean other = text.equals("other");
            // violation above """String literal expressions should be on
            // the left side of an equals comparison."""
            super();
            if (result) {
                return;
            }
        }
    }

    static class After {
        After(String text) {
            super();
            boolean result = text.equalsIgnoreCase("value");
            if (result) {
                return;
            }
        }
    }
}
