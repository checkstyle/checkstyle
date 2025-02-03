/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="id" value="stringEqual"/>
    <property name="files" value="Example2.java"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example2 {

  public void checkStringEquality(String str1, String str2) {
    // filtered violation ''.equals()' should be used for string comparison'
    if (str1 == str2) {
      System.out.println("Strings are equal!");
    }
  }

  public static void main(String[] args) {
    Example2 example = new Example2();
    example.checkStringEquality("test", "test");
  }

}
// xdoc section -- end
