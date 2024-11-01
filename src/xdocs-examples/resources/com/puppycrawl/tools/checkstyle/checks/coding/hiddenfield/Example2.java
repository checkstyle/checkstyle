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
class Example2 {

  private String field;
  private String testField;

  Example2(String testField) { // OK, 'testField' param doesn't hide any field
  }
  void method(String param) {
    String field = param; // violation, ''field' hides a field'
  }
  void setTestField(String testField) {
    this.field = field;
  }
  Example2 setField(String field) {
    this.field = field;
    return null;
  }
  abstract class Inner {
    abstract int method(String field);
  }
}
// xdoc section -- end
