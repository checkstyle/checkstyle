/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalSymbol">
      <property name="symbolCodes" value="0x1F600-0x1F64F"/>
      <property name="tokens" value="STRING_LITERAL"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

// xdoc section -- start
public class Example4 {
  // 😀 This emoji in comment is ok
  String normal = "Hello World";

  String withEmoji = "Hello 😀"; // violation 'Illegal Unicode symbol detected'
}
// xdoc section -- end

