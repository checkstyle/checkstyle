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
// violation 6 lines above 'use SLF4J instead.'

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.filters.suppresswarningsfilter;

public class Example2 {
  @SuppressWarnings("checkstyle:systemout")
  public static void foo() {
    System.out.println("Debug info."); // filtered violation 'use SLF4J instead.'
  }
  public static void boo() {
    System.out.println("Some info."); // violation 'use SLF4J instead.'
  }
}
// xdoc section -- end
