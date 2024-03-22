/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterNumber"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.parameternumber;

// xdoc section -- start
class Example1 extends ExternalService1 {

  @JsonCreator
  // violation below, 'More than 7 parameters (found 8)'
  Example1(int a, int b, int c, int d,
           int e, int f, int g, int h) {}

  // violation below, 'More than 7 parameters (found 8)'
  Example1(String a, String b, String c, String d,
           String e, String f, String g, String h) {}

  @Override
  // violation below, 'More than 7 parameters (found 8)'
  public void processData(String a, String b, String c, String d,
                          String e, String f, String g, String h) {}

}

class ExternalService1 {

  // violation below, 'More than 7 parameters (found 8)'
  public void processData(String a, String b, String c, String d,
                          String e, String f, String g, String h) {}

}
// xdoc section -- end
@interface JsonCreator {}
