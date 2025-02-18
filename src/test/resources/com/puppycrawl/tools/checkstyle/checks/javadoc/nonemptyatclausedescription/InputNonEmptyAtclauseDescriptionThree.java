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

class InputNonEmptyAtclauseDescriptionThree {

  /**
   * Some summary.
   *
   * @param j some param
   * @see <a href="www.checkstyle.org/">website</a>some description.
   * @see <a href="www.checkstyle.org/">website</a>...
   * "some description"
   */
  public int endwithDot(int j){
    return j+1;
  }

  /**
   * Some summary.
   *
   * @param j some param
   * @see <a href="https://docs.oracle.com/">
   * keyPairGenerator Algorithms</a>-there is some description.
   *
   * @see #endwithDot(int) <p>some descriptoin</p>
   */
  public int testMethod2(int j){
    return j-1;
  }

  /**
   * Some summary.
   *
   * @param j some param
   * @see <a href="...">a</a><p>ending with paragraph tag</p>.
   * @see <a href="...">b</a><h1>ending with h tag</h1>
   *
   * @see <a href="...">c</a>
   * <h1>ending with h tag</h1>.
   */
  public int testWithHtmlTags(int j){
    return j-1;
  }

  /**
   * some docs.
   * @param k some param
   *
   * @see <a
   *     href="https://docs.oracle.com/en/java/standard-names.html#keypairgenerator-algorithms">
   *     KeyPairGenerator Algorithms</a>.
   *
   * @return j
   */
  public int testMethod(int k) {
    return k+1;
  }
}
