/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property name="format" value="[ \t]+$"/>
      <property name="illegalPattern" value="true"/>
      <property name="message" value="Trailing whitespace"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;

// xdoc section -- start
public class Example8 {
  private void foo() { 
    // violation above 'Trailing whitespace'
  }
}
// xdoc section -- end
