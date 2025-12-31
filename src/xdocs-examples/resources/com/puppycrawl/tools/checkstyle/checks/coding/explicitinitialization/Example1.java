/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ExplicitInitialization"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.explicitinitialization;

// xdoc section -- start
public class Example1 {
  private int intField1 = 0; // violation 'Variable 'intField1' explicitly initialized to '0' (default value for its type)'
  private int intField2 = 1;
  private int intField3;

  private char charField1 = '\0'; // violation 'Variable 'charField1' explicitly initialized to '\\0' (default value for its type)'
  private char charField2 = 'b';
  private char charField3;

  private boolean boolField1 = false; // violation Variable 'boolField1' explicitly initialized to 'false' (default value for its type)
  private boolean boolField2 = true;
  private boolean boolField3;

  private Object objField1 = null; // violation 'Variable 'objField1' explicitly initialized to 'null' (default value for its type)'
  private Object objField2 = new Object();
  private Object objField3;

  private int arrField1[] = null; // violation 'Variable 'arrField1' explicitly initialized to 'null' (default value for its type)'
  private int arrField2[] = new int[10];
  private int arrField3[];
}
// xdoc section -- end
