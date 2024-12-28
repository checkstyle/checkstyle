/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NonEmptyAtclauseDescription"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.nonemptyatclausedescription;

// xdoc section -- start
class Example1 {

  /**
   * Some summary.
   *
   * @param a Some description
   * @param b
   * @deprecated
   * @throws Exception
   * @return
   */
  public void testMethod(){
    // violation 6 lines above 'At-clause should have a non-empty description'
    // violation 6 lines above 'At-clause should have a non-empty description'
    // violation 6 lines above 'At-clause should have a non-empty description'
    // violation 6 lines above 'At-clause should have a non-empty description'
  }
}
// xdoc section -- end
