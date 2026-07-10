/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AtclauseOrder"/>
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
* @since Some javadoc.
* @serial Some javadoc.
* @serialField field Object Field description.
* @serialData
*/
public class Example1 {
  class Valid implements Serializable {}
  // ok below 'Block tags have to appear in the order'

  /**
   * Some javadoc.
   *
   * @version Some javadoc.
   * @see Some javadoc.
   * @since Some javadoc.
   * @deprecated
   */
  class Invalid implements Serializable {}
  // ok below 'Block tags have to appear in the order'

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
