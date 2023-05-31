/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = (default)false
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

class Example1 {
  abstract class AbstractFirst {} // OK
  abstract class Second {} // violation '.* must match pattern '\^Abstract\.\+\$''
  class AbstractThird {} // violation '.* must be declared as 'abstract''
  class Fourth {} // OK
  abstract class GeneratorFifth {} // violation '.* must match pattern '\^Abstract\.\+\$''
  class GeneratorSixth {} // OK
}
