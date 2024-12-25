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

public class Example2 {
   @SuppressWarnings("checkstyle:systemout") // This suppresses the RegexpSinglelineJava check
public static void foo() {
    System.out.println("Debug info."); // should NOT fail RegexpSinglelineJava
}

}
// xdoc section -- end
