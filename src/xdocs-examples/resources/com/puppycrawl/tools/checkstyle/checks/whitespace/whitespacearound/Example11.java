/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="allowEmptySwitchBlockStatements" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

// xdoc section -- start
class Example11 {

    void method(int value) {
        // allowed: empty switch block when allowEmptySwitchBlockStatements is true
        switch (value) {
        }
    }
}
// xdoc section -- end
