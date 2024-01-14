/*xml
<module name="Checker">
  <module name="FileLength">
    <property name="max" value="5"/>
    <property name="fileExtensions" value="txt"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.filelength;

// xdoc section -- start
public class Example3 {
  public void myTest() {
    // small class with more than 5 lines
    String test = "Some content"; // no violation as only txt files are validated
  }
}
// xdoc section -- end
