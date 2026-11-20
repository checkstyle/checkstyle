/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NPathComplexity">
      <property name="max" value="1"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.metrics.npathcomplexity;

// xdoc section - start
class Example3 {
  void multiCatch() { // violation 'NPath Complexity is 2 (max allowed is 1).'
    try {
      work();
    } catch (IllegalArgumentException | IllegalStateException ex) {
      recover();
    } finally {
      cleanUp();
    }
  }

  void separateCatches() { // violation 'NPath Complexity is 3 (max allowed is 1).'
    try {
      work();
    } catch (IllegalArgumentException ex) {
      recover();
    } catch (IllegalStateException ex) {
      recover();
    }
  }

  void resource() throws Exception {
    try (AutoCloseable resource = this::cleanUp) {
      work();
    }
  }

  void work() {}
  void recover() {}
  void cleanUp() {}
}
// xdoc section - end
