/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingOverrideOnRecordAccessor"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverrideonrecordaccessor;

// xdoc section -- start
record Person(String name, int age) {

  // violation below, 'method must include @java.lang.Override annotation.'
  public String name() {
    return name.toUpperCase();
  }

  public String name(String value) {
    return value;
  }

  @Override
  public int age() {
    return age;
  }

  public int age(int value) {
    return value;
  }
}
// xdoc section -- end

class Example1 {}
