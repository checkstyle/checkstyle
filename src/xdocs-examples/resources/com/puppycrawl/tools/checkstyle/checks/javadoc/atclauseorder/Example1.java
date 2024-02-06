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
* @serialField
* @serialData
* @deprecated Some javadoc.
*/

public class Example1 {}

class Valid implements Serializable
{
}

/**
* Some javadoc.
*
* @since Some javadoc.
* @version Some javadoc. // violation
* @deprecated
* @see Some javadoc. // violation
* @author Some javadoc. // violation
*/

// violations on lines 40, 42 and 43, order of tags does not match default order

class Invalid implements Serializable
{
}
// xdoc section -- end
