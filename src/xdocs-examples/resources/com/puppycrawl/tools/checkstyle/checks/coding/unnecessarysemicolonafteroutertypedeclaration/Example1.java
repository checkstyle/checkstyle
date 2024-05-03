/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessarySemicolonAfterOuterTypeDeclaration"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicolonafteroutertypedeclaration;

// xdoc section -- start
class Example1 {
  class Nested {

  }; // OK, nested type declarations are ignored

}; // violation

interface B {

}; // violation

enum C {

}; // violation

@interface D {

}; // violation
// xdoc section -- end
