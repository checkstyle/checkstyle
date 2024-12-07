/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SummaryJavadoc">
      <property name="period" value="。"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

// xdoc section -- start
class Example3 {

  /**
   * {@inheritDoc}
   */
  public String m1(){ return ""; }
  // violation below, 'Summary javadoc is missing'
  /** */
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
  // violation 3 lines above 'Summary of Javadoc is missing an ending period'
  /**
   * This method returns nothing.
   */
  void m6() {}
  // violation 4 lines above 'First sentence of Javadoc is missing an ending period'
  /**
  * {@summary This is a java doc without period。}
  */
  public void m7() {}

  /**
  * {@summary First sentence is normally the summary。
  * Use of html tags:
  * <ul>
  * <li>Item one.</li>
  * <li>Item two.</li>
  * </ul>}
  */
  public void m8() {}
  // violation 8 lines above 'Summary of Javadoc is missing an ending period'
}
// xdoc section -- end
