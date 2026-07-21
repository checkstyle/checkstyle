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
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.filters.suppresswarningsfilter;

public class Example1 {

  @SuppressWarnings("memberName")
  int J; // filtered violation 'must match pattern'
  int JJ; // violation 'must match pattern'

  @SuppressWarnings({"MemberName", "NoWhitespaceAfter"})
  int [] ARRAY; // filtered violation
  // filtered violation above
  int [] ARRAY2; // violation
  // violation above
  @SuppressWarnings("checkstyle:systemout")
  public static void foo() {
    System.out.println("Debug info.");
  }

  public static void boo() {
    System.out.println("Some info.");
  }
}
// xdoc section -- end
