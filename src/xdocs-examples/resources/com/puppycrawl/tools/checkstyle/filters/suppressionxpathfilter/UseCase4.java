/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions2.xml"/>
    </module>
    <module name="EmptyLineSeparator"/>
  </module>
</module>
*/

// filtered violation below "'package' should be separated from previous line"
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

import javax.annotation.processing.Generated;

// xdoc section -- start
// violation 3 lines below "'VARIABLE_DEF' should be separated from previous line"
public class UseCase4 {
  int age = 23;
  private int wordCount = 11;
  public void SetSomeVar() {} // violation 'should be separated from previous line'
  public void DoMATH() {}     // violation 'should be separated from previous line'

  public void throwsMethod() throws RuntimeException {}

  final public void legacyMethod() {
    strictfp abstract class Legacy {}
  }

  public void changeAge() {
    age = 24;
  }

  public void testMethod() {
    int TestVariable;
    int WeirdName;
  }

  public void sayHelloWorld() {
    if (age > 0 && wordCount > 0) {
      System.out.println("Hello");
    }
    else if (age < 0) {
      System.out.println("World");
    }
  }

  @Generated("first")
  public void Test1() {}

  @Generated("second")
  public void Test2() {}
}
// xdoc section -- end
