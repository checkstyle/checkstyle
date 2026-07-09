/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions3.xml"/>
    </module>
    <module name="LeftCurly">
      <property name="option" value="nl"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;
import javax.annotation.processing.Generated;

// xdoc section -- start
// filtered violation below "'{' at column 23 should be on a new line."
public class UseCase5 {
  int age = 23;
  private int wordCount = 11;
  public void SetSomeVar() {}
  public void DoMATH() {}

  public void throwsMethod() throws RuntimeException {}
  // violation below, "'{' at column 36 should be on a new line."
  final public void legacyMethod() {
    strictfp abstract class Legacy {}
  }
  // violation below, "'{' at column 27 should be on a new line."
  public void changeAge() {
    age = 24;
  }
  // violation below, "'{' at column 28 should be on a new line."
  public void testMethod() {
    int TestVariable;
    int WeirdName;
  }
  // violation below, "'{' at column 31 should be on a new line."
  public void sayHelloWorld() {// violation below, 'should be on a new line."
    if (age > 0 && wordCount > 0) {
      System.out.println("Hello");
    } // violation below, "'{' at column 23 should be on a new line."
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
