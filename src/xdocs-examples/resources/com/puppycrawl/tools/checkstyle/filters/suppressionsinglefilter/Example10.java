/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="message" value="Name 'log' must match pattern"/>
  </module>
  <module name="JavadocMethodCheck"/>
  <module name="MagicNumberCheck"/>
  <module name="IndentationCheck"/>
  <module name="NamingConventionCheck"/>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example10 {
  // violation below, ''logFile' doesn't match the required naming pattern'
  private String logFile = "app.log";

  // Method to process log file
  public void processLog() {
    System.out.println("Processing log file: " + logFile);
  }

  public static void main(String[] args) {
    Example10 example = new Example10();
    example.processLog();
  }
}
// xdoc section -- end
