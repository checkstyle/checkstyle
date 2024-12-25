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

}; // violation 'Unnecessary semicolon after outer type declaration'

interface B {

}; // violation 'Unnecessary semicolon after outer type declaration'

enum C {

}; // violation 'Unnecessary semicolon after outer type declaration'

@interface D {

}; // violation 'Unnecessary semicolon after outer type declaration'
// xdoc section -- end
