/*
AbstractClassName


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

class Example1 {
  abstract class AbstractFirst {}
  abstract class Second {} // violation 'must match pattern '\^Abstract\.\+\$''
  class AbstractThird {} // violation 'must be declared as 'abstract''
  class Fourth {}
  abstract class GeneratorFifth {}
  // violation above 'must match pattern '\^Abstract\.\+\$''
  class GeneratorSixth {}
}
