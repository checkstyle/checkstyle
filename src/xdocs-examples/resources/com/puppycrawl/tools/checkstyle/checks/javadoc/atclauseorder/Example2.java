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
* @since Some javadoc. // violation
* @serial Some javadoc.
* @serialField Field description.
* @serialData
*/
public class Example2 {
  class Valid implements Serializable {
  }

  /**
   * Some javadoc.
   *
   * @version Some javadoc.
   * @see Some javadoc.
   * @since Some javadoc. // violation
   * @deprecated  // violation
   */
  class Invalid implements Serializable {
  }

  /**
   * Some javadoc.
   *
   * @author max
   * @version Some javadoc.
   * @see Some javadoc.
   * @since Some javadoc. // violation
   * @deprecated // violation
   */
  enum Test {}
}
// xdoc section -- end
