/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SummaryJavadoc">
      <property name="forbiddenReturnFragments"
        value="^the empty string*"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

// xdoc section -- start
class Example4 {

  /**
   * {@inheritDoc}
   */
  public String m1(){ return ""; }
  // violation below 'Forbidden summary fragment'
  /** {@return the empty string} */
  public String m2(){ return ""; }

  /**
   * {@summary  }
   */
  public String m3(){ return ""; }
  // violation 3 lines above 'Summary javadoc is missing'
  /**
   * {@summary <p> <p/>}
   */
  public String m4() { return ""; }
  // violation 3 lines above 'Summary javadoc is missing'
  /**
   * {@summary <p>This is a javadoc with period.<p/>}
   */
  public void m5() {}

  /**
   * This method returns nothing.
   */
  void m6() {}

  /**
  * {@summary This is a java doc with period symbol。}
  */
  public void m7() {}
  // violation 3 lines above 'Summary of Javadoc is missing an ending period'
  /**
  * {@summary First sentence is normally the summary.
  * Use of html tags:
  * <ul>
  * <li>Item one.</li>
  * <li>Item two.</li>
  * </ul>}
  */
  public void m8() {}

}
// xdoc section -- end
