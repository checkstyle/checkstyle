/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HiddenField"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

// xdoc section -- start
class Example1 {

  private String field;
  private String testField;

  Example1(String testField) { // violation, 'field' hides a field
  }
  void method(String param) {
    String field = param; // violation, ''field' hides a field'
  }
  void setTestField(String testField) { // violation, ''testField' hides a field'
    this.field = field;
  }
  Example1 setField(String field) { // violation, ''field' hides a field'
    this.field = field;
    return null;
  }
  abstract class Inner {
    abstract int method(String field); // violation, ''field' hides a field'
  }
}
// xdoc section -- end
