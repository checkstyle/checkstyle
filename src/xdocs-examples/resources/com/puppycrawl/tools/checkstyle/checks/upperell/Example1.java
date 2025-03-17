/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UpperEll"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.upperell;

// xdoc section -- start
class Example1 {
  long var1 = 508987; // OK
  long var2 = 508987l; // violation 'Should use uppercase 'L'.'

  long var3 = 508987L; // OK
}
// xdoc section -- end
