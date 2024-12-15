/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocType">
      <property name="allowMissingParamTags" value="true"/>
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
public class Example6 {
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
  public class ClassE<T> {}

  /** */
  private class ClassF<T> {}

  /** */
  @Generated
  public class ClassG<T> {}
}
// xdoc section -- end
