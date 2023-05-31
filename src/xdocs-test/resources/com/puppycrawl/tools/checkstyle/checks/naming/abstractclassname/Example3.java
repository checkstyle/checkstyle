/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = (default)false
ignoreName = true


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

class Example3 {
  abstract class AbstractFirst {} // OK
  abstract class Second {} // OK
  class AbstractThird {} // violation '.* must be declared as 'abstract''
  class Fourth {} // OK
  abstract class GeneratorFifth {} // OK
  class GeneratorSixth {} // OK
}
