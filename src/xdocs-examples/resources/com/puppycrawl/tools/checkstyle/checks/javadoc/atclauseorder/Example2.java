/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AtclauseOrder"/>
      <property name="target" value="CLASS_DEF"/>
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
* @serialField
* @serialData
* @deprecated Some javadoc.
*/

public class Example2 {}

class Valid2 implements Serializable {}

/**
* Some javadoc.
*
* @since Some javadoc.
* @version Some javadoc. // violation
* @deprecated
* @see Some javadoc. // violation
* @author Some javadoc. // violation
*/

class Invalid2 implements Serializable {}
// xdoc section -- end
