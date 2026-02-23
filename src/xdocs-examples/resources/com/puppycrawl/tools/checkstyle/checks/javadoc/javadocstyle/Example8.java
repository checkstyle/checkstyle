/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocStyle">
      <property name="endOfSentenceFormat"
        value="[。]$"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

// xdoc section -- start
public class Example8 {

  /**
   * This sentence ends with Japanese period。
   */
  void ok() {}

  /**
   * This sentence missing Japanese period
   */
  // violation 3 lines above 'First sentence should end with a period.'
  void violation() {}
}
// xdoc section -- end
