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
class InputNonEmptyAtclauseDescriptionThree {

  /**
   * Some summary.
   *
   * @param j some param
   * @see <a href="www.checkstyle.org/">website</a>some description
   */
  public int endwithDot(int j){
    return j+1;
  }

  /**
   * Some summary.
   *
   * @param j some param
   * @see <a href="https://docs.oracle.com/">
   * keyPairGenerator Algorithms</a>there is some description.
   */
  public int testMethod(int j){
    return j-1;
  }
  
  /**
   * Some summary.
   *
   * @param j some param
   * @see description
   */
  public int testMethod2(int j){
    return j-1;
  }
}
// xdoc section -- end
