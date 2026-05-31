/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoLineWrap"/>
  </module>
</module>


*/

// non-compiled with javac: Compilable with Java25
// xdoc section -- start
package com.puppycrawl. // violation 'should not be line-wrapped'
  tools.checkstyle.checks.whitespace.nolinewrap;

import com.puppycrawl.tools. // violation 'should not be line-wrapped'
  checkstyle.api.AbstractCheck;

import module // violation 'should not be line-wrapped'
  java.base;

import static java.math. // violation 'should not be line-wrapped'
  BigInteger.ZERO;
// xdoc section -- end

class Example1 { }
