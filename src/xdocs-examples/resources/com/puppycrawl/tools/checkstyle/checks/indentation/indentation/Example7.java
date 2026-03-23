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
    void method()
            throws Exception {  // ok, throwsIndent = 8 (col 12 = method col 4 + 8)
    }

    void method2()
        throws Exception {      // violation, 'level 8, expected level should be 12'
    }
}
// xdoc section -- end
