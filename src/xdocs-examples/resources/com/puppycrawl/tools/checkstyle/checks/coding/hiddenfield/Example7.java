/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HiddenField">
      <property name="ignoreAbstractMethods" value="true"/>
    </module>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

// xdoc section -- start
abstract class Example7 {

  private String field;

  Example7(int field) { // violation, 'field' param hides a 'field' field
    this.field = Integer.toString(field);
  }

  public abstract int method(String field); // OK
}
// xdoc section -- end
