/*xml
<module name="Checker">
  <module name="LineEnding"/>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.lineending;
// xdoc section -- start
public class Example1 { // ␊ // ok line ending is LF
  public void method() { // ␊ // ok line ending is LF
    int x = 1; // ␊ // ok line ending is LF
  } // ␊ // ok line ending is LF
} // ␊ // ok line ending is LF
// xdoc section -- end
