/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalClass">
      <property name="ignoreAnnotatedBy"
        value="Configuration, java.lang.Deprecated" />
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

// xdoc section -- start
@java.lang.Deprecated // ok, skipped by annotation
class Example2 {

  private Example2() {
  }
}

class Example2A { // violation, 'Class Example2A should be declared as final.'

  private Example2A() {
  }
}

@Configuration // ok, skipped by annotation
class Example2B {

  private Example2B() {
  }
}
// xdoc section -- end
