/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AtclauseOrder">
      <property name="tagOrder"
                value="@author, @since, @version, @param, @return,
                       @throws, @exception, @deprecated,
                       @see, @serial, @serialField, @serialData"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.atclauseorder;

import java.io.Serializable;

// xdoc section -- start
// violation 11 lines below 'Block tags have to appear in the order'
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
public class Example2 {
  class Valid implements Serializable {}
  // violation 8 lines below 'Block tags have to appear in the order'
  // violation 8 lines below 'Block tags have to appear in the order'
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
  // violation 5 lines above 'Block tags have to appear in the order'
}
// xdoc section -- end
