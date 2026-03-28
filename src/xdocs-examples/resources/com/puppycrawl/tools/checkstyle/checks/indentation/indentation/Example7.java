/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="throwsIndent" value="8"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example7 {
    void method1() throws Exception {}

    void method2()
            throws Exception {}  // ok, throwsIndent

    void method3()
            throws Exception {}  // ok, throwsIndent
}
// xdoc section -- end
