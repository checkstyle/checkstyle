/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyLineSeparator">
      <property name="allowMultipleEmptyLines" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public class InputEmptyLineSeparatorCrash18660 {
    // This comment is at the start of the class body and used to crash EmptyLineSeparatorCheck

    void display(boolean ifYes) {
        if (true) {
            System.out.println("Hello world");
        }
    }
}
