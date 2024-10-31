/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HiddenField"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

// xdoc section -- start
public class Example1 {

  private String field;
  private String testField;

  public Example1(String testField) { // violation
  }
  public void method(String param) { // OK
    String field = param; // violation, 'field' variable hides 'field' field
  }
  public void setTestField(String testField) { // violation
    this.field = field;
  }
  public Example1 setField(String field) { // violation
    this.field = field;
    return null;
  }
}
// xdoc section -- end
