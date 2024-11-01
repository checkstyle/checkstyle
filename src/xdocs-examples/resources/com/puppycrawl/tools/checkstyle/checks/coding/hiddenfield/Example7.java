/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HiddenField">
      <property name="ignoreAbstractMethods" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

// xdoc section -- start
abstract class Example7 {

  private String field;
  private String testField;

  Example7(int field) { // violation, ''field' hides a field'
    this.field = Integer.toString(field);
  }
  public abstract int method(String field); // OK, because it match ignoreFormat
  void setTestField(String testField) { // violation, 'testField' hides a field'
    this.field = field;
  }
  Example1 setField(String field) { // violation, ''field' hides a field'
    this.field = field;
    return null;
  }
}
// xdoc section -- end
