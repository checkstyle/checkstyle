/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnusedTryResourceShouldBeUnnamed"/>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedtryresourceshouldbeunnamed;

// xdoc section -- start
class Example1 {

  void test() {
    // violation below, 'Unused try resource 'a' should be unnamed'
    try (AutoCloseable a = lock()) {

    } catch (Exception e) {

    }

    try (AutoClosable _ = lock()) {

    } catch (Exception e) {

    }
  }

  AutoCloseable lock() {
    return null;
  }
}
// xdoc section -- end
