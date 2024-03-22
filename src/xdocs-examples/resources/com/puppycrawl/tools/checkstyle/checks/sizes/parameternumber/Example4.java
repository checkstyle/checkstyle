/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterNumber">
      <property name="ignoreAnnotatedBy" value="JsonCreator"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.parameternumber;

// xdoc section -- start
class Example4 extends ExternalService4 {

  @JsonCreator
  // ok below, constructor annotated with JsonCreator annotation is ignored
  Example4(int a, int b, int c, int d,
           int e, int f, int g, int h) {}

  // violation below, 'More than 7 parameters (found 8)'
  Example4(String a, String b, String c, String d,
           String e, String f, String g, String h) {}

  @Override
  // violation below, 'More than 7 parameters (found 8)'
  public void processData(String a, String b, String c, String d,
                          String e, String f, String g, String h) {}

}

class ExternalService4 {

  // violation below, 'More than 7 parameters (found 8)'
  public void processData(String a, String b, String c, String d,
                          String e, String f, String g, String h) {}

}
// xdoc section -- end
