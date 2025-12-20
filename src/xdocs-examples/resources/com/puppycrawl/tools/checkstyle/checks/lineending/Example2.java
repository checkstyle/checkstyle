/*xml
<module name="Checker">
  <module name="LineEnding">
    <property name="lineEnding" value="crlf"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.lineending;
// xdoc section -- start
public class Example2 { // ␍␊
    public void method() { // ␍␊
        int x = 1; // ␍␊
    } // ␍␊
} // ␍␊ // ok, file uses CRLF line endings.
// xdoc section -- end