/*xml
<module name="Checker">
  <module name="SuppressionFilter">
    <property name="file" value="nonexisting.xml"/>
    <property name="optional" value="true"/>
  </module>
  <module name="TreeWalker">
    <module name="MemberName"/>
    <module name="MagicNumber"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionfilter;
// xdoc section - start
public class Example3 {

  // violation below 'Name 'MyVariable' must match pattern'
  int MyVariable;

  int a = 10; // violation ''10' is a magic number.'

  public void exampleMethod() {

    int num = 100; // violation ''100' is a magic number.'

    if (true) {
      // violation above 'Must have at least one statement.'
    }
  }
}
// xdoc section - end
