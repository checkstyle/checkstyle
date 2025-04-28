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
class Example4 {

  private String field;
  private String testField;

  Example4(String testField) { // ok, because ignoreConstructorParameter is true
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
  abstract class Inner {
    abstract int method(String field); // violation, ''field' hides a field'
  }
}
// xdoc section -- end
