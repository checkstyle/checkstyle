/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoWhitespaceBefore">
      <property name="tokens" value="METHOD_REF, DOT"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

import com.google.common.collect.Lists;

// xdoc section -- start
class Example3 {
  void example() {
    Lists.charactersOf("foo").listIterator()
         .forEachRemaining(System.out::print);
    // violation above ''.' is preceded with whitespace'
    Lists.charactersOf("foo").listIterator().forEachRemaining(System.out ::print);
    // violation above ''::' is preceded with whitespace'
    Lists.charactersOf("foo").listIterator().forEachRemaining(System.out::print);
  }
}
// xdoc section -- end
