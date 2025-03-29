/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod">
        <property name="allowInlineReturn" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

// xdoc section -- start
public class Example8 {

  /**
   * {@return the foo}
   */
  public int getFoo() { return 0; }

  /**
   * Returns the bar
   * @return the bar
   */
  public int getBar() { return 0; }

}
// xdoc section -- end
