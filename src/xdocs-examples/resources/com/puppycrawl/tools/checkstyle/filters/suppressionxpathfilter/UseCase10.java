/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions8.xml"/>
    </module>
    <module name="RequireThis">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;
import javax.annotation.processing.Generated;

// xdoc section -- start

public class UseCase10 {
  int age = 23;
  private int wordCount = 11;
  public void SetSomeVar() {}
  public void DoMATH() {}

  public void throwsMethod() throws RuntimeException {}

  final public void legacyMethod() {
    strictfp abstract class Legacy {}
  }

  public void changeAge() {
    age = 24; // filtered violation 'Reference to instance variable'
  }

  public void testMethod() {
    int TestVariable;
    int WeirdName;
  }

  public void sayHelloWorld() { // violation below 'Reference to instance variable'
    if (age > 0 && wordCount > 0) { // violation 'Reference to instance variable'
      System.out.println("Hello");
    }
    else if (age < 0) { // violation 'Reference to instance variable'
      System.out.println("World");
    }
  }

  @Generated("first")
  public void Test1() {}

  @Generated("second")
  public void Test2() {}
}
// xdoc section -- end
