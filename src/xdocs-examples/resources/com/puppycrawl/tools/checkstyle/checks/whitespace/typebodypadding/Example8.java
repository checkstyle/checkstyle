/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding">
      <property
        name="tokens"
        value="INTERFACE_DEF, RECORD_DEF, ENUM_DEF, ANNOTATION_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// xdoc section -- start
class Example8 {
  int x;
}

// violation below 'Blank line required after the opening brace of type definition.'
interface Example8b {
  int x = 1;
} // violation 'Blank line required before the closing brace of type definition.'
// xdoc section -- end
