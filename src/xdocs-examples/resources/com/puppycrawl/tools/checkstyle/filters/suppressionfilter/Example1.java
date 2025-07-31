/*xml
<module name="Checker">
  <module name="SuppressionFilter">
    <property name="file" value="suppressionexample1.xml"/>
    <property name="optional" value="false"/>
  </module>
  <module name="TreeWalker">
    <module name="JavadocStyle"/>
    <module name="MagicNumber"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionfilter;
// xdoc section -- start
public class Example1 {

  // filtered violation below 'First sentence should end with a period.'
  /**
   * This field a is missing period
   */
  int a = 10; // filtered violation ''10' is a magic number.'

  public void exampleMethod() {

    int num = 100; // filtered violation ''100' is a magic number.'

    if (true) {
      // violation above 'Must have at least one statement.'
    }
  }
}
// xdoc section -- end
