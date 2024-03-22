/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterNumber">
      <property name="ignoreOverriddenMethods" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.parameternumber;

// xdoc section -- start
class Example3 extends ExternalService3 {

  @JsonCreator
  // violation below, 'More than 7 parameters (found 8)'
  Example3(int a, int b, int c, int d,
           int e, int f, int g, int h) {}

  // violation below, 'More than 7 parameters (found 8)'
  Example3(String a, String b, String c, String d,
           String e, String f, String g, String h) {}

  @Override
  // ok below, overridden method is ignored
  public void processData(String a, String b, String c, String d,
                          String e, String f, String g, String h) {}

}

class ExternalService3 {

  // violation below, 'More than 7 parameters (found 8)'
  public void processData(String a, String b, String c, String d,
                          String e, String f, String g, String h) {}

}
// xdoc section -- end
