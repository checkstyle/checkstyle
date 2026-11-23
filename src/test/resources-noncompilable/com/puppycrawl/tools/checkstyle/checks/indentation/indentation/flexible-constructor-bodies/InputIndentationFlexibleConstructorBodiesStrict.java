/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="braceAdjustment" value="0"/>
      <property name="caseIndent" value="2"/>
      <property name="throwsIndent" value="2"/>
      <property name="arrayInitIndent" value="2"/>
      <property name="lineWrappingIndentation" value="4"/>
      <property name="forceStrictCondition" value="true"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

class InputIndentationFlexibleConstructorBodiesStrict {

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
