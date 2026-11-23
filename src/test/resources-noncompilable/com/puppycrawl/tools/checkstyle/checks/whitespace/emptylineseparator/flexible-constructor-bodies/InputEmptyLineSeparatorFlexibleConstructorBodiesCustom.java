/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyLineSeparator">
      <property name="allowNoEmptyLineBetweenFields" value="true"/>
      <property name="allowMultipleEmptyLines" value="false"/>
      <property name="allowMultipleEmptyLinesInsideClassMembers" value="false"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

class InputEmptyLineSeparatorFlexibleConstructorBodiesCustom {

    static class Members {
        private int first;
        private int second;

        Members(boolean stop) {
            int local = 1; // violation 'There is more than 1 empty line after this line.'


            super();
            if (stop) {
                return;
            }
        }


        void method() { // violation ''METHOD_DEF' has more than 1 empty lines before.'
        }
    }
}
