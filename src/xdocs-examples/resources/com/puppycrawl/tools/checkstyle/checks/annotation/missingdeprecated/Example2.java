/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingDeprecated">
      <property name="violateExecutionOnNonTightHtml" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

// xdoc section -- start
class Example2 {
  @Deprecated
  public static final int MY_CONST = 13; // ok

  /** This javadoc is missing deprecated tag. */
  // violation below '@deprecated Javadoc tag with description.'
  @Deprecated
  public static final int COUNTER = 10;

  /**
   * @deprecated
   * <p></p>
   */
  @Deprecated
  public static final int NUM = 123456; // ok

  /**
   * @deprecated
   * <p> // violation, 'Unclosed HTML tag found: p'
  */
  @Deprecated
  public static final int CONST = 12;
}
// xdoc section -- end
