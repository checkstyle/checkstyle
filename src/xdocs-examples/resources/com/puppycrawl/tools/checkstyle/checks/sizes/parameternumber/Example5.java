/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterNumber">
      <property name="max" value="6"/>
      <property name="ignoreOverriddenMethods" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.parameternumber;

// xdoc section -- start
class Example5 extends ExternalService5 {

  @JsonCreator
  Example5(int a, int b, int c, int d,
           int e, int f, int g, int h) {}
  // violation 2 lines above 'More than 6 parameters (found 8)'
  // violation below 'More than 6 parameters (found 8)'
  Example5(String a, String b, String c, String d,
           String e, String f, String g, String h) {}

  @Override
  public void processData(String a, String b, String c, String d,
                          String e, String f, String g, String h) {}

}

class ExternalService5 {

  // violation below, 'More than 6 parameters (found 8)'
  public void processData(String a, String b, String c, String d,
                          String e, String f, String g, String h) {}

}
// xdoc section -- end
