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
* @serialField
* @serialData
*/
public class Example2 {}

class Valid2 implements Serializable {}

/**
 * Some javadoc.
 *
 * @since Some javadoc.
 * @version Some javadoc.
 * @deprecated
 * @see Some javadoc.
 */
class Invalid2 implements Serializable {}

/**
 * Some javadoc.
 *
 * @since Some javadoc.
 * @version Some javadoc.
 * @deprecated Some javadoc.
 * @see Some javadoc.
 * @author max // violation
 */
enum Test2 {}
// xdoc section -- end
