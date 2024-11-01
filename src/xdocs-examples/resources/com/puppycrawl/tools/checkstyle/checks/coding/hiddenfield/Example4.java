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
abstract class Example4 {

  private String field;
  private String testField;

  Example4(String testField) { // OK, 'testField' param doesn't hide any field
  }
  void method(String param) {
    String field = param; // violation, ''field' hides a field' field
  }
  void setTestField(String testField) { // violation, ''testField' hides a field'
    this.field = field;
  }
  Example4 setField(String field) { // violation, ''field' hides a field'
    this.field = field;
    return null;
  }
}
// xdoc section -- end
