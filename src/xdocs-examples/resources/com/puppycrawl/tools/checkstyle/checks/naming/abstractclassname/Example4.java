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
  abstract class AbstractFirst {} // violation 'must match pattern'
  abstract class Second {} // violation 'must match pattern'
  class AbstractThird {}
  class Fourth {}
  abstract class GeneratorFifth {}
  // violation below 'must be declared as 'abstract''
  class GeneratorSixth {}
}
// xdoc section -- end
