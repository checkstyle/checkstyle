/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocStyle">
      <property name="endOfSentenceFormat"
        value="([.?!][ \t\n\r\f&lt;])|([.?!]$)"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

// xdoc section -- start
/**
 * Some description here
 */
public class Example8 {
  Example8() {
    // violation 5 lines above 'First sentence should end with a period.'
  }
}
// xdoc section -- end
