/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EqualsAvoidNull"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

class InputEqualsAvoidNullFlexibleConstructorBodies {

    static class Before {
        Before(String text) {
            boolean result = text.equals("value");
            // violation above """String literal expressions should be on
            // the left side of an equals comparison."""
            boolean other = text.equalsIgnoreCase("other");
            // violation above """String literal expressions should be on
            // the left side of an equalsIgnoreCase comparison."""
            super();
            if (result) {
                return;
            }
        }
    }

    static class After {
        After(String text) {
            super();
            boolean result = "value".equals(text);
            boolean other = text.equals("value");
            // violation above """String literal expressions should be on
            // the left side of an equals comparison."""
            if (other) {
                return;
            }
        }
    }
}
