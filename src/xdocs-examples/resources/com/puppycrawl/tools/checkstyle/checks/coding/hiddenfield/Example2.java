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

  Example2(String testField) { // ok, because PARAMETER_DEF not configured
  }
  void method(String param) {
    String field = param; // violation, ''field' hides a field'
  }
  void setTestField(String testField) { // ok, because PARAMETER_DEF not configured
    this.field = field;
  }
  Example2 setField(String field) { // ok, because PARAMETER_DEF not configured
    this.field = field;
    return null;
  }
  abstract class Inner {
    abstract int method(String field); // ok, because PARAMETER_DEF not configured
  }
}
// xdoc section -- end
