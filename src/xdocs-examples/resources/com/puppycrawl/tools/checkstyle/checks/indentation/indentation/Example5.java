/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="basicOffset" value="2"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example5 {

    int value = 10;

    void method() {
        if (value > 0) {
            value++;
        }
    }
}
// xdoc section -- end