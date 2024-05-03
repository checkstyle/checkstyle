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

}; // violation

interface T {

}; // OK

enum U {

}; // OK

@interface V {

}; // OK
// xdoc section -- end

