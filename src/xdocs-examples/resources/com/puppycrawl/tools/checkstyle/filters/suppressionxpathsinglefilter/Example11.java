/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RequireThis">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="RequireThis"/>
      <property name="query" value="//CLASS_DEF[./IDENT[@text='Example11']]
            //METHOD_DEF[./IDENT[@text='changeAge']]//ASSIGN/IDENT[@text='age']"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

// xdoc section -- start
public class Example11 {
  private int age = 23;
  private int number = 100;

  public void changeAge() {
    // filtered violation below 'Reference to instance variable 'age' needs "this.".'
    age = 24;
  }
  public void changeNumber(int number) {
    // violation below, 'Reference to instance variable 'number' needs "this.".'
    number = number;
  }
}
// xdoc section -- end
