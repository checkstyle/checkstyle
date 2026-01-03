/*xml
<module name="Checker">
  <module name="LineEnding"/>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.lineending;
// xdoc section -- start
public class Example1 { // ␊
  public void method() { // ␊
    int x = 1; // ␊
  } // ␊
} // ␊ // ok, file uses LF line endings.
// xdoc section -- end
