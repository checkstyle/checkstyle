/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HiddenField">
      <property name="tokens" value="VARIABLE_DEF"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

// xdoc section -- start
public class Example2 {

  private String field;
  private String testField;

  public Example2(String testField) { // OK, 'testField' param doesn't hide any field
  }
  public void method(String param) { // OK
    String field = param; // violation, 'field' variable hides 'field' field
  }
  public void setTestField(String testField) { // OK
    this.field = field;
  }
  public Example2 setField(String field) { // OK
    this.field = field;
    return null;
  }
}
// xdoc section -- end
