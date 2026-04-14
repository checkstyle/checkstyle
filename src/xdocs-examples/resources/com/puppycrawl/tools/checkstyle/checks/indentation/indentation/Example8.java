/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="arrayInitIndent" value="2"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example8 {
    int[] values1 = {
      10,                 // ok, arrayInitIndent = 2 (col 6 = array col 4 + 2)
      20,
      30
    };

    int[] values2 = {
  10                      // violation, 'level 2, expected level should be one of'
    };
}
// xdoc section -- end
