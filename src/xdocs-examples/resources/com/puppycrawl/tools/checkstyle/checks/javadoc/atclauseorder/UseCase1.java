/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AtclauseOrder">
      <property name="target" value="CLASS_DEF"/>
      <property name="tagOrder"
                value="@version, @author, @param, @return,
                       @throws, @exception, @see, @since,
                       @serial, @serialField, @serialData"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.atclauseorder;

import java.io.Serializable;
// xdoc section - start
// violation 5 lines below 'Block tags have to appear in the order'
/**
* Some javadoc.
*
* @author Some javadoc.
* @version Some javadoc.
* @param Some javadoc.
* @return Some javadoc.
* @throws Some javadoc.
* @exception Some javadoc.
* @see Some javadoc.
* @since Some javadoc.
* @serial Some javadoc.
* @serialField field Object Field description.
* @serialData
*/
public class UseCase1 {
  class Valid implements Serializable {}

  // ok below, ENUM_DEF is not checked because target is CLASS_DEF
  /**
   * Some javadoc.
   *
   * @author max
   * @version Some javadoc.
   * @see Some javadoc.
   * @since Some javadoc.
   * @deprecated
   */
  enum Test {}
  /**
   * Some javadoc.
   *
   * @return Some javadoc.
   * @param a Some javadoc.
   */
  public int foo(int a) {
    return a;
  }
  // ok above, METHOD_DEF is not checked because target is CLASS_DEF
}
// xdoc section - end
