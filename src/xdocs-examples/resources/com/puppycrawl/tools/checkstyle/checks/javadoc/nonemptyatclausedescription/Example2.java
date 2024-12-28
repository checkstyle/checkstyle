/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NonEmptyAtclauseDescription">
      <property name="javadocTokens" value="PARAM_LITERAL,THROWS_LITERAL"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.nonemptyatclausedescription;

// xdoc section -- start
class Example2 {

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
    // violation 5 lines above 'At-clause should have a non-empty description'
    // @deprecated ignored as not mentioned in javadocTokens
    // @return ignored as not mentioned in javadocTokens
  }
}
// xdoc section -- end
