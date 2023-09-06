/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AbstractClassName">
      <property name="format" value="^Generator.+$"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

// xdoc section -- start
class Example4 {
  // violation below 'must match pattern'
  abstract class AbstractFirst {}
  abstract class Second {} // violation 'must match pattern'
  class AbstractThird {}
  class Fourth {}
  abstract class GeneratorFifth {}
  class GeneratorSixth {} // violation 'must be declared as 'abstract''
}
// xdoc section -- end
