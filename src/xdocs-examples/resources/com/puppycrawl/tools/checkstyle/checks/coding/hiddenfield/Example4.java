/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HiddenField">
      <property name="ignoreConstructorParameter" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

// xdoc section -- start
public class Example4 {

  private String field;
  private String testField;

  public Example4(String testField) { // OK, 'testField' param doesn't hide any field
  }
  public void method(String param) { // OK
    String field = param; // violation, 'field' variable hides 'field' field
  }
  public void setTestField(String testField) { // violation, 'testField' variable
    // hides 'testField' field
    this.field = field;
  }

  public Example4 setField(String field) { // violation
    this.field = field;
    return null;
  }
}
// xdoc section -- end
