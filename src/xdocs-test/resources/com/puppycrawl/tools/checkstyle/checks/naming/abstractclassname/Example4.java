/*
AbstractClassName
format = ^Generator.+$
ignoreModifier = (default)false
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

class Example4 {
  abstract class AbstractFirst {} // violation '.* must match pattern '\^Generator\.\+\$''
  abstract class Second {} // violation '.* must match pattern '\^Generator\.\+\$''
  class AbstractThird {} // OK
  class Fourth {} // OK
  abstract class GeneratorFifth {} // OK
  class GeneratorSixth {} // violation '.* must be declared as 'abstract''
}
