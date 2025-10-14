/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocMethod">
      <property name="minLineCount" value="2"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

// xdoc section -- start
public class Example7 {
  public Example7() {}
  public void testMethod1() {}

  public void testMethod2() { // violation, 'Missing a Javadoc comment'
    System.out.println("line 1");
    System.out.println("line 2");
    System.out.println("line 3");
  }

  /**
   * This method has javadoc.
   */
  public void testMethod3() {
    System.out.println("line 1");
    System.out.println("line 2");
    System.out.println("line 3");
  }
}
// xdoc section -- end
