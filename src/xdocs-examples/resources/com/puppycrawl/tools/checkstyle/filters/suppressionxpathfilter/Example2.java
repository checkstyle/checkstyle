/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions0.xml"/>
      <property name="optional" value="false"/>
    </module>
    <module name="CyclomaticComplexity">
      <property name="max" value="3"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;
import javax.annotation.processing.Generated;

// xdoc section -- start

public class Example2 {
  int age = 23;
  private int wordCount = 11;
  public void SetSomeVar() {}
  public void DoMATH() {}

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

  public void sayHelloWorld() { // violation 'Cyclomatic Complexity is 4'
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
