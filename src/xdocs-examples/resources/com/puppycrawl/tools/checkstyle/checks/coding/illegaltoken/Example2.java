/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalToken">
      <property name="tokens" value="LITERAL_NATIVE"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltoken;

// xdoc section -- start
class Example2 {
  native void InvalidExample(); // violation, 'Using 'native' is not allowed'

  void anotherMethod() {
    outer:
    for (int i = 0; i < 5; i++) {
      if (i == 1) {
        break outer;
      }
    }
  }
}
// xdoc section -- end
