/*xml
<module name="Checker">
  <module name="LineEnding">
    <property name="lineEnding" value="crlf"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.lineending;
// xdoc section -- start
public class Example2 { // ␍␊ // ok line ending is CRLF
  public void method() { // ␍␊ // ok line ending is CRLF
    int x = 1; // ␍␊ // ok line ending is CRLF
  } // ␍␊ // ok line ending is CRLF
} // ␍␊ // ok line ending is CRLF
// xdoc section -- end
