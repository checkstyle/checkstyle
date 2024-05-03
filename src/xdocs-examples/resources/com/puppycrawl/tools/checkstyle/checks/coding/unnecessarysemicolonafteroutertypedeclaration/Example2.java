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
class E {

}; // violation

interface F {

}; // OK

enum G {

}; // OK

@interface H {

}; // OK
// xdoc section -- end

