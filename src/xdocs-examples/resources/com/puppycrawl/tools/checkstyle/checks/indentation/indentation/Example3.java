/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="forceStrictCondition" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example3 {
    boolean x, y;

    void method(int a,
        int b) {            // ok, lineWrappingIndentation=4 from method
        if (x
            && y) {         // ok, lineWrappingIndentation=4 from 'if'
            method(a, b);
        }
    }

    void method2(int a,
                 int b) {   // violation, 'level 17, expected level should be 8'
        if (x
                && y) {     // violation, 'level 16, expected level should be 12'
            method2(a, b);
        }
    }
}
// xdoc section -- end
