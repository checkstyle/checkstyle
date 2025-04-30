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
class Example5 {

  private String field;
  private String testField;

  Example5(String testField) { // violation, ''testField' hides a field'
  }
  void method(String param) {
    String field = param; // violation, ''field' hides a field'
  }
  void setTestField(String testField) { // ok, because ignoreSetter is true
    this.field = field;
  }
  Example5 setField(String field) { // violation, ''field' hides a field'
    this.field = field;
    return null;
  }
  abstract class Inner {
    abstract int method(String field); // violation, ''field' hides a field'
  }
}
// xdoc section -- end
