/*xml
<module name="Checker">
  <module name="FileLength">
    <property name="max" value="5"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.filelength;

// xdoc section -- start
// violation first line 'File length is 19 lines (max allowed is 5)'
public class Example2 {
  public void myTest() {
    // small class with more than 5 lines less than 2000 lines
    String test = "Some content";
  }
}
// xdoc section -- end
