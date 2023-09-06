/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoLineWrap">
      <property name="tokens" value="IMPORT"/>
    </module>
  </module>
</module>


*/

// xdoc section -- start
package com.puppycrawl. // OK, PACKAGE_DEF is not part of the tokens
  tools.checkstyle.checks.whitespace.nolinewrap;

import java.io.*;
import java.lang. // violation 'should not be line-wrapped'
  Boolean;

import static java.math. // OK, STATIC_IMPORT is not part of the tokens
  BigInteger.ZERO;
// xdoc section -- end

class Example4 { }
