/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalSymbol"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

// xdoc section -- start
public class Example1 {
  // ✅ Enhancement completed // violation 'Illegal symbol detected: '✅''
  // 😀 Happy coding          // violation 'Illegal symbol detected: '😀''
  int value1 = 1;
  String value2 = "Hello 😀";
}
// xdoc section -- end
