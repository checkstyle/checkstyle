/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TrailingComment">
      <property name="legalComment" value="^ (SUPPRESS CHECKSTYLE|NOPMD|NOSONAR)$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

// xdoc section -- start
public class Example3 {
  int a; // SUPPRESS CHECKSTYLE
  int b; // NOPMD
  int c; // NOSONAR
  int d; // violation, not suppressed
}
// xdoc section -- end
