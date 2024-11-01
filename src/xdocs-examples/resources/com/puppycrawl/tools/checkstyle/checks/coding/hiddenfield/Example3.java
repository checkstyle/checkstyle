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
abstract class Example3 {

  private String field;
  private String testField;

  Example3(String testField) { // OK, because it match ignoreFormat
  }
  void method(String param) {
    String field = param; // violation, ''field' hides a field'
  }
  void setTestField(String testField) { // OK, because it match ignoreFormat
    this.field = field;
  }
  Example3 setField(String field) { // violation, ''field' hides a field'
    this.field = field;
    return null;
  }
}
// xdoc section -- end
