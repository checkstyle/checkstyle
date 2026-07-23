/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions7.xml"/>
    </module>
    <module name="MagicNumber"/>
    <module name="MethodName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;
import javax.annotation.processing.Generated;

// xdoc section -- start

public class UseCase9 {
  int age = 23; // violation "'23' is a magic number."
  private int wordCount = 11; // violation "'11' is a magic number."
  public void SetSomeVar() {}  // violation "Name 'SetSomeVar' must match pattern"
  public void DoMATH() {}  // filtered violation "Name 'DoMATH' must match pattern"

  public void throwsMethod() throws RuntimeException {}

  final public void legacyMethod() {
    strictfp abstract class Legacy {}
  }

  public void changeAge() {
    age = 24; // violation "'24' is a magic number."
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
  public void Test1() {} // violation "Name 'Test1' must match pattern"

  @Generated("second")
  public void Test2() {} // violation "Name 'Test2' must match pattern"
}
// xdoc section -- end
class Main {
  int someField = 11; // violation "'11' is a magic number."
  void FOO() {} // filtered violation "Name 'FOO' must match pattern"
}
