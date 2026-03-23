/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="braceAdjustment" value="2"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example9 {
    void method()
      {               // ok, braceAdjustment = 2 (col 6 = method col 4 + braceAdj 2)
      }               // ok, closing brace at col 6

    void method2()
    {                 // violation, 'level 4, expected level should be 6'
    }                 // violation, 'level 4, expected level should be 6'
}
// xdoc section -- end
