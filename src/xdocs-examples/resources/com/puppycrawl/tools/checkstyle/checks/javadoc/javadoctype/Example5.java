/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocType">
      <property name="scope" value="private"/>
      <property name="excludeScope" value="package"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;
import javax.annotation.processing.Generated;
// xdoc section -- start
/**
 * @author a
 * @version $Revision1$
 */
public class Example5 {
  /**
   * @author a
   * @version $Revision1$
   */
  public class ClassA {
    /** */
    private class ClassB {}
  }

  /**
   * @author
   * @version abc
   * @unknownTag value
   */
  public class ClassC {}

  /** */
  public class ClassD {}

  /** */
  public class ClassE<T> {}

  /** */
  private class ClassF<T> {} // violation, as param tag for <T> is missing

  /** */
  @Generated("tool")
  public class ClassG<T> {}
}
// xdoc section -- end
