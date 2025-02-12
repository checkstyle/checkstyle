/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example10.java"/>
    <property name="message" value="Name 'log' must match pattern"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example10 {

  // filtered violation 'Name 'log' must match pattern'
  private String log = "Some log message";

  public static void main(String[] args) {
    Example10 example = new Example10();
    System.out.println(example.log);
  }

}
// xdoc section -- end
