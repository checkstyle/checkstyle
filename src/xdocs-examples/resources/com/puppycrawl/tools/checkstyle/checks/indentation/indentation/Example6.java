/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="lineWrappingIndentation" value="8"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example6 {
    boolean x, y;

    void method(int a,
            int b) {        // ok, lineWrappingIndentation = 8 (col 12 = method col 4 + 8)
        if (x
                && y) {     // ok, lineWrappingIndentation = 8 (col 16 = 'if' col 8 + 8)
        }
    }

    void method2(int a,
        int b) {            // violation, 'incorrect indentation level 8, expected level should be 12'
        if (x
            && y) {         // violation, 'incorrect indentation level 12, expected level should be 16'
        }
    }
}
// xdoc section -- end
