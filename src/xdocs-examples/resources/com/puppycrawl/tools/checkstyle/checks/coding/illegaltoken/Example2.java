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
  native void InvalidExample(); // violation 'IllegalToken', 'Using 'native' is not allowed'
}
// xdoc section -- end
