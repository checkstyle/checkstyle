/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HiddenField">
      <property name="ignoreSetter" value="true"/>
      <property name="setterCanReturnItsClass" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

// xdoc section -- start
public class Example6 {

  private String field;
  private String testField;

  public Example6(String testField) { // violation
  }
  public void method(String param) { // OK
    String field = param; // violation, 'field' variable hides 'field' field
  }
  public void setTestField(String testField) { // OK
    this.field = field;
  }
  public Example6 setField(String field) { // OK
    this.field = field;
    return null;
  }
}
// xdoc section -- end
