/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions12.xml"/>
    </module>
    <module name="MagicNumber"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;
import javax.annotation.processing.Generated;

// xdoc section -- start

public class UseCase1 {
  int age = 23;  // violation "'23' is a magic number."
  private int wordCount = 11; // filtered violation "'11' is a magic number."
  public void SetSomeVar() {}
  public void DoMATH() {}

  public void throwsMethod() throws RuntimeException {}

  final public void legacyMethod() {
    strictfp abstract class Legacy {}
  }

  public void changeAge() {
    age = 24;  // violation "'24' is a magic number."
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
