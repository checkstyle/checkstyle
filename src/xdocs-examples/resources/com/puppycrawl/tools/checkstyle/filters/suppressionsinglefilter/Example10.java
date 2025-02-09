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

  // This line should violate the pattern check for the variable name 'log'
  private String log = "Some log message";  // Violates naming pattern rule

  public static void main(String[] args) {
    Example10 example = new Example10();
    System.out.println(example.log);
  }

}
// xdoc section -- end
