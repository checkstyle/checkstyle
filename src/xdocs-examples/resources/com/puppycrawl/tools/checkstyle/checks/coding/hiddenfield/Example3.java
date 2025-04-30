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
class Example3 {

  private String field;
  private String testField;

  Example3(String testField) { // ok, because it match ignoreFormat
  }
  void method(String param) {
    String field = param; // violation, ''field' hides a field'
  }
  void setTestField(String testField) { // ok, because it match ignoreFormat
    this.field = field;
  }
  Example3 setField(String field) { // violation, ''field' hides a field'
    this.field = field;
    return null;
  }
  abstract class Inner {
    abstract int method(String field); // violation, ''field' hides a field'
  }
}
// xdoc section -- end
