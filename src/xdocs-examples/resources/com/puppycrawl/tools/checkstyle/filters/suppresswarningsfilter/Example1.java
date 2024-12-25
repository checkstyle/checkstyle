/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressWarningsHolder" />
  </module>
  <module name="SuppressWarningsFilter" />
</module>
*/

// xdoc section -- start

package com.puppycrawl.tools.checkstyle.filters.suppresswarningsfilter;

public class Example1 {
    @SuppressWarnings("memberName")
private int J; // should NOT fail MemberNameCheck

@SuppressWarnings({"MemberName", "NoWhitespaceAfter"})
private int [] ARRAY; // should NOT fail MemberNameCheck and NoWhitespaceAfterCheck
}
// xdoc section -- end
