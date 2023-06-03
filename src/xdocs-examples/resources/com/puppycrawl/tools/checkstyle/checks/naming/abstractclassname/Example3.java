/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = (default)false
ignoreName = true


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

class Example3 {
  abstract class AbstractFirst {}
  abstract class Second {}
  class AbstractThird {} // violation '.* must be declared as 'abstract''
  class Fourth {}
  abstract class GeneratorFifth {}
  class GeneratorSixth {}
}
