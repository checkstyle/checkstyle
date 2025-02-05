/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files"
      value=".+\.(?:txt|xml|csv|sh|thrift|html|sql|eot|ttf|woff|css|png)$"/>
    <property name="checks" value=".*"/>
  </module>
  <module name="MagicNumberCheck"/>
  <module name="JavadocMethodCheck"/>
  <module name="FinalClassCheck"/>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example7 {

  private static final int NUMBER = 10;

  /**
   * Method that demonstrates checkstyle suppression.
   */
  public void showSuppressedMethod() {
    System.out.println("The number is: " + NUMBER);
  }

  public static void main(String[] args) {
    Example7 example = new Example7();
    example.showSuppressedMethod();
  }
}
// xdoc section -- end
