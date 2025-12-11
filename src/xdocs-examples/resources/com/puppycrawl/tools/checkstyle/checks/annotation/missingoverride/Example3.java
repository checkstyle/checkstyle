/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingOverride"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

// xdoc section -- start
record Example3(String name, int age) {

  @Override
  public String name() {
    return name;
  }

  public int age() {
    // violation above, 'record accessor and must include @java.lang.Override'
    return age;
  }
}
// xdoc section -- end
