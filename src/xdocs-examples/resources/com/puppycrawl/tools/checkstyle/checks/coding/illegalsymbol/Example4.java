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
  // ok, 😀 comment in emoji

  int value1 = 1;
  String value2 = "Hello 😀"; // violation 'Illegal symbol detected: '😀''
}
// xdoc section -- end

