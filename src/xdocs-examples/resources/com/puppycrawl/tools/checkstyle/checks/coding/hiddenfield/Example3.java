/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HiddenField">
      <property name="ignoreFormat" value="^testField"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

// xdoc section -- start
public class Example3 {

  private String field;
  private String testField;

  public Example3(String testField) { // OK, because it match ignoreFormat
  }
  public void method(String param) { // OK
    String field = param; // violation, 'field' variable hides 'field' field
  }
  public void setTestField(String testField) { // OK, because it match ignoreFormat
    this.field = field;
  }

  public Example3 setField(String field) { // violation
    this.field = field;
    return null;
  }
}
// xdoc section -- end
