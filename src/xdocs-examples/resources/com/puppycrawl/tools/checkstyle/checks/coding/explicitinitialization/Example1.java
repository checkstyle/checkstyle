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
  private int intField1 = 0; // violation 'Explicit Initialization'
  private int intField2 = 1;
  private int intField3;

  private char charField1 = '\0'; // violation 'Explicit Initialization'
  private char charField2 = 'b';
  private char charField3;

  private boolean boolField1 = false; // violation 'Explicit Initialization'
  private boolean boolField2 = true;
  private boolean boolField3;

  private Object objField1 = null; // violation 'Explicit Initialization'
  private Object objField2 = new Object();
  private Object objField3;

  private int arrField1[] = null; // violation 'Explicit Initialization'
  private int arrField2[] = new int[10];
  private int arrField3[];
}
// xdoc section -- end
