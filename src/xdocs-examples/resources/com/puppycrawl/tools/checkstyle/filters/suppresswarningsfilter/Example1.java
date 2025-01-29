/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressWarningsHolder" />
    <module name="MemberName" />
    <module name="NoWhitespaceAfter" />
  </module>
  <module name="SuppressWarningsFilter" />
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppresswarningsfilter;
// xdoc section -- start
public class Example1 {
  @SuppressWarnings("memberName")
  int J; //only because there is annotation
  int JJ; // violation 'must match pattern'

  @SuppressWarnings({"MemberName", "NoWhitespaceAfter"})
  int [] ARRAY; //only because there is annotation for both Checks
  int [] ARRAY2;
  // 2 violations above:
  //   ''int' is followed by whitespace'
  //   'must match pattern'
}
// xdoc section -- end
