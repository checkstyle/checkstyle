/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocType">
      <property name="versionFormat" value="\$Revision.*\$"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

// xdoc section -- start
/**
 * @author a
 * @version $Revision1$
 */
public class Example4 {
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
  // violation 3 lines above 'Unknown tag 'unknownTag''
  /** */
  public class ClassD {}

  /** */
  public class ClassE<T> {} // violation, as param tag for <T> is missing

  /** */
  private class ClassF<T> {} // violation, as param tag for <T> is missing

  /** */
  @Generated
  public class ClassG<T> {}
}
// xdoc section -- end
