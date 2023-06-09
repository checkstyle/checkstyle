/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = true
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

class Example2 {
  abstract class AbstractFirst {}
  abstract class Second {} // violation 'must match pattern '\^Abstract\.\+\$''
  class AbstractThird {}
  class Fourth {}
  abstract class GeneratorFifth {} // violation 'must match pattern '\^Abstract\.\+\$''
  class GeneratorSixth {}
}
