/*xml
<module name="Checker">
  <module name="LineEnding">
    <property name="lineEnding" value="cr"/>
    <property name="fileExtensions" value="txt"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.lineending;
// xdoc section -- start
public class Example4 {  // ␊ // ok, only .txt files are checked
  public void method() { // ␊ // ok, only .txt files are checked
    int x = 1; // ␊           // ok, only .txt files are checked
  } // ␊                      // ok, only .txt files are checked
} // ␊                        // ok, only .txt files are checked
// xdoc section -- end
