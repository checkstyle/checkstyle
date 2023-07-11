/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AbstractClassName"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

// xdoc section -- start
class Example1 {
  abstract class AbstractFirst {}
  abstract class Second {} // violation 'must match pattern'
  class AbstractThird {} // violation 'must be declared as 'abstract''
  class Fourth {}
  abstract class GeneratorFifth {}
  // violation above 'must match pattern'
  class GeneratorSixth {}
}
// xdoc section -- end
