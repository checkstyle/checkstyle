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
class Example7 {

  private String field;
  private String testField;

  Example7(String testField) { // violation ''field' hides a field
  }
  void method(String param) {
    String field = param; // violation ''field' hides a field'
  }
  void setTestField(String testField) { // violation 'testField' hides a field'
    this.field = field;
  }
  void setField(String field) { // violation ''field' hides a field'
    this.field = field;
  }
  abstract class Inner {
    abstract int method(String field); // ok, because ignoreAbstractMethods is true
  }
}
// xdoc section -- end
