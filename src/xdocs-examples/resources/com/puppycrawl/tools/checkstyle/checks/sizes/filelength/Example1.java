/*xml
<module name="Checker">
  <module name="FileLength"/>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.filelength;

// xdoc section -- start
public class Example1 {
  public void myTest() {
    String test = "No violation is on small class less than 2000 lines.";
  }
}
// xdoc section -- end
