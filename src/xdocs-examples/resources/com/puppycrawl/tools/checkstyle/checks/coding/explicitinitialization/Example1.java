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
  // violation below ''intField1' explicitly initialized to '0''
  private int intField1 = 0;
  private int intField2 = 1;
  private int intField3;

  // violation below ''charField1' explicitly initialized to '\\0''
  private char charField1 = '\0';
  private char charField2 = 'b';
  private char charField3;

  // violation below ''boolField1' explicitly initialized to 'false''
  private boolean boolField1 = false;
  private boolean boolField2 = true;
  private boolean boolField3;

  // violation below ''objField1' explicitly initialized to 'null''
  private Object objField1 = null;
  private Object objField2 = new Object();
  private Object objField3;

  // violation below ''arrField1' explicitly initialized to 'null''
  private int arrField1[] = null;
  private int arrField2[] = new int[10];
  private int arrField3[];
}
// xdoc section -- end
