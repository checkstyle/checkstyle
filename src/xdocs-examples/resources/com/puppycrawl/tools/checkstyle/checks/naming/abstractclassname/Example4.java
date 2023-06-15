/*
AbstractClassName
format = ^Generator.+$


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

class Example4 {
  // violation below 'must match pattern '\^Generator\.\+\$''
  abstract class AbstractFirst {}
  abstract class Second {} // violation 'must match pattern '\^Generator\.\+\$''
  class AbstractThird {}
  class Fourth {}
  abstract class GeneratorFifth {}
  class GeneratorSixth {} // violation 'must be declared as 'abstract''
}
