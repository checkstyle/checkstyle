/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TrailingComment">
      <property name="legalComment" value="^ (SUPPRESS CHECKSTYLE|NOPMD|NOSONAR).*"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

// xdoc section -- start
public class UseCase1 {
  int a; // SUPPRESS CHECKSTYLE - OK, comment starts with " SUPPRESS CHECKSTYLE"
  int b; // NOPMD - OK, comment starts with " NOPMD"
  int c; // NOSONAR - OK, comment starts with " NOSONAR"
  int d; // violation 'Don't use trailing comments.'
}
// xdoc section -- end
