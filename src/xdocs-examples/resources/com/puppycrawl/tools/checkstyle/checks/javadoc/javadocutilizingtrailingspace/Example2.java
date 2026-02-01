/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocUtilizingTrailingSpace">
      <property name="lineLimit" value="100"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

// xdoc section -- start
public class Example2 {
    /**
     * This is a long line that would violate with default lineLimit of 80,
     * but is OK with lineLimit of 100.
     */
    public void method() { }
}
// xdoc section -- end
