/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocType">
      <property name="tokens" value="CLASS_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;
import javax.annotation.processing.Generated;
// xdoc section - start
/**
 * @author
 * @version 1.0
 */
public class Example9 {
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
  public class ClassE<T> {}  // violation 'missing @param '<T>' tag.'

  /** */
  private class ClassF<T> {} // violation 'missing @param '<T>' tag.'

  /** */
  @Generated("tool")
  public class ClassG<T> {}

  /** */
  public interface InterfaceH<T> {} // ok, because INTERFACE_DEF is not in tokens
}
// xdoc section - end
