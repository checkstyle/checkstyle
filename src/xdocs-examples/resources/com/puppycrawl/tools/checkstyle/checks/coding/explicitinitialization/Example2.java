/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ExplicitInitialization">
      <property name="onlyObjectReferences" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.explicitinitialization;

// xdoc section -- start
public class Example2 {
  private int intField1 = 0; // ignored
  private int intField2 = 1;
  private int intField3;

  private char charField1 = '\0'; // ignored
  private char charField2 = 'b';
  private char charField3;

  private boolean boolField1 = false; // ignored
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
