/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions13.xml"/>
    </module>
    <module name="MethodName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;
import javax.annotation.processing.Generated;
// xdoc section -- start

public class UseCase2 {
  int age = 23; // violation 2 lines below "Name 'SetSomeVar' must match pattern"
  private int wordCount = 11;
  public void SetSomeVar() {}
  public void DoMATH() {}
  // violation above, "Name 'DoMATH' must match pattern"
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
  // filtered violation 2 lines below "Name 'Test1' must match pattern"
  @Generated("first")
  public void Test1() {}
  // filtered violation 2 lines below "Name 'Test2' must match pattern"
  @Generated("second")
  public void Test2() {}
}
// xdoc section -- end
