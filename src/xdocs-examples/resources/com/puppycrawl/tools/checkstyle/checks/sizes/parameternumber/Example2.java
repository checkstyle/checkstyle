/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterNumber">
      <property name="max" value="10"/>
      <property name="tokens" value="METHOD_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.parameternumber;

// xdoc section -- start
class Example2 extends ExternalService2 {

  @JsonCreator
  // ok below, constructor is not in tokens to check
  Example2(int a, int b, int c, int d,
           int e, int f, int g, int h) {}

  // ok below, constructor is not in tokens to check
  Example2(String a, String b, String c, String d,
           String e, String f, String g, String h) {}

  @Override
  // ok below, less than 10 parameters (found 8)
  public void processData(String a, String b, String c, String d,
                          String e, String f, String g, String h) {}

}

class ExternalService2 {

  // ok below, less than 10 parameters (found 8)
  public void processData(String a, String b, String c, String d,
                          String e, String f, String g, String h) {}

}
// xdoc section -- end
