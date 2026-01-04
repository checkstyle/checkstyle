/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingOverrideOnRecordAccessor"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverrideonrecordaccessor;

// xdoc section -- start
interface Printable {
  void print();
}

record Document(String title) implements Printable {

  // violation below 'method must include @java.lang.Override annotation.'
  public String title() {
    return title.toUpperCase();
  }

  public void print() {
    System.out.println(title);
  }
}
// xdoc section -- end

class Example2 {}
