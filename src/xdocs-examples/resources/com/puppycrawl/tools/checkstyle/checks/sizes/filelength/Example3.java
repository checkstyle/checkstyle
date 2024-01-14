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
    // Ok, as only txt file with more than 5 lines will be considered as violation
  }
}
// xdoc section -- end
