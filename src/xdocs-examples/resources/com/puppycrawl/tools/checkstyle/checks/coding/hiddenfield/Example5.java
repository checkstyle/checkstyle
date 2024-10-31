/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HiddenField">
      <property name="ignoreSetter" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

// xdoc section -- start
public class Example5 {

  private String field;
  private String testField;

  public Example5(String testField) { // violation
  }
  public void method(String param) { // OK
    String field = param; // violation, 'field' variable hides 'field' field
  }
  public void setTestField(String testField) { // OK
    this.field = field;
  }

  public Example5 setField(String field) { // violation
    this.field = field;
    return null;
  }
}
// xdoc section -- end
