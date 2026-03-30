/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example1 {
    int a;                      // ok, basicOffset = 4

    void method() {
        int b = 0;              // ok, basicOffset = 4
    }

    void method2()
            throws Exception {  // ok, throwsIndent = 4
        switch (a) {
            case 1:             // ok, caseIndent = 4
                break;
        }
    }

    int[] values = {
        1, 2, 3                 // ok, arrayInitIndent = 4
    };
}
// xdoc section -- end
