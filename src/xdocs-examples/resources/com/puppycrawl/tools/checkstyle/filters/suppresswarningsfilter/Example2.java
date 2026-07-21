/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressWarningsHolder" />
    <module name="RegexpSinglelineJava">
      <property name="id" value="systemout"/>
      <property name="format" value="^.*System\.(out|err).*$"/>
      <property name="message"
        value="Don't use System.out/err, use SLF4J instead."/>
    </module>
  </module>
  <module name="SuppressWarningsFilter" />
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.filters.suppresswarningsfilter;
// violation 9 lines above 'use SLF4J instead.'
public class Example2 {

  @SuppressWarnings("memberName")
  int J;
  int JJ;

  @SuppressWarnings({"MemberName", "NoWhitespaceAfter"})
  int [] ARRAY;

  int [] ARRAY2;

  @SuppressWarnings("checkstyle:systemout")
  public static void foo() {
    System.out.println("Debug info."); // filtered violation 'use SLF4J instead.'
  }

  public static void boo() {
    System.out.println("Some info."); // violation 'use SLF4J instead.'
  }
}
// xdoc section -- end
