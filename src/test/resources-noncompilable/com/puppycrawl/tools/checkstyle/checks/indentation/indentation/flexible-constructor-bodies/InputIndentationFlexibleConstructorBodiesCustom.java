/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="basicOffset" value="4"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

class InputIndentationFlexibleConstructorBodiesCustom {

    static class Before {
        Before(boolean stop) {
            int local = 1;
          int other = 2;
          // violation above """'ctor def' child has incorrect indentation
          // level 10, expected level should be 12."""
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
