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

  public Example7(int field) { // violation, 'field' param hides a 'field' field
    float field; // violation, 'field' variable hides a 'field' field
  }
  public abstract int method(String field); // OK
}

public class Demo extends Example7 {

  public int method(String param){
    return param;
  }
}
// xdoc section -- end
