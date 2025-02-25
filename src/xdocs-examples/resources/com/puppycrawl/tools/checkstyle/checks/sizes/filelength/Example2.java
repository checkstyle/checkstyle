/*xml
<module name="Checker">
  <module name="FileLength">
    <property name="max" value="5"/>
  </module>
</module>
*/
// violation 7 lines above 'File length is 18 lines (max allowed is 5)'
package com.puppycrawl.tools.checkstyle.checks.sizes.filelength;

// xdoc section -- start
public class Example2 {
  public void myTest() {
    // small class with more than 5 lines
    String test = "Some content"; // there is violation
  }
}
// xdoc section -- end
