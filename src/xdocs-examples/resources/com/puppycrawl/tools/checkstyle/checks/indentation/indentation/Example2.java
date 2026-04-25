/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="caseIndent" value="0"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example2 {
    void method(int x) {
        switch (x) {
        case 1:             // ok, caseIndent = 0 (same level as switch body)
            break;
        case 2:             // ok
            break;
            case 3:         // violation, 'level 12, expected level should be 8'
            break;
        }
    }
}
// xdoc section -- end
