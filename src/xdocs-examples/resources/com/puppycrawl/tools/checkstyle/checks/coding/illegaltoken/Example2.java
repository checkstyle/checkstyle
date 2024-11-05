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
public class Example2 {
  public native void InvalidExample(); // violation, 'Using 'native' is not allowed'
}
// xdoc section -- end
