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

  }; // ok, nested type declarations are ignored

}; // violation 'Unnecessary semicolon'

interface B {

}; // violation 'Unnecessary semicolon'

enum C {

}; // violation 'Unnecessary semicolon'

@interface D {

}; // violation 'Unnecessary semicolon'
// xdoc section -- end
