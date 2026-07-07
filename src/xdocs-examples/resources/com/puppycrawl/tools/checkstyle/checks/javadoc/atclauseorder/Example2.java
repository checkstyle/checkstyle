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
// violation 11 lines below 'Block tags have to appear in the order .\[@author, @since, @version, @param, @return, @throws, @exception, @deprecated, @see, @serial, @serialField, @serialData\].'
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
* @serialField Field description.
* @serialData
*/
public class Example2 {
  class Valid implements Serializable {
  }

  // violation 7 lines below 'Block tags have to appear in the order .\[@author, @since, @version, @param, @return, @throws, @exception, @deprecated, @see, @serial, @serialField, @serialData\].'
  // violation 7 lines below 'Block tags have to appear in the order .\[@author, @since, @version, @param, @return, @throws, @exception, @deprecated, @see, @serial, @serialField, @serialData\].'
  /**
   * Some javadoc.
   *
   * @version Some javadoc.
   * @see Some javadoc.
   * @since Some javadoc.
   * @deprecated
   */
  class Invalid implements Serializable {
  }

  // violation 8 lines below 'Block tags have to appear in the order .\[@author, @since, @version, @param, @return, @throws, @exception, @deprecated, @see, @serial, @serialField, @serialData\].'
  // violation 8 lines below 'Block tags have to appear in the order .\[@author, @since, @version, @param, @return, @throws, @exception, @deprecated, @see, @serial, @serialField, @serialData\].'
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
}
// xdoc section -- end
