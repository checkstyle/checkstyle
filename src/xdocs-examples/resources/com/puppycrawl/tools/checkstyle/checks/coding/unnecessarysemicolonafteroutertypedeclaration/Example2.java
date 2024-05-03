/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessarySemicolonAfterOuterTypeDeclaration">
      <property name="tokens" value="CLASS_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicolonafteroutertypedeclaration;

// xdoc section -- start
class Example2 {
  class Nested {

  }; // OK, nested type declarations are ignored

}; // violation

interface T {

};

enum U {

};

@interface V {

};
// xdoc section -- end

