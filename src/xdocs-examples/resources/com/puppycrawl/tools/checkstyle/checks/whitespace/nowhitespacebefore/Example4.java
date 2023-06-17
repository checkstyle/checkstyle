/*
NoWhitespaceBefore
tokens = METHOD_REF, DOT
allowLineBreaks = true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

import com.google.common.collect.Lists;

class Example4 {
  void example() {
    // xdoc section -- start
    Lists .charactersOf("foo") // violation ''.' is preceded with whitespace'
          .listIterator()
          .forEachRemaining(System.out ::print);
    // violation above ''::' is preceded with whitespace'
    Lists.charactersOf("foo")
         .listIterator()
         .forEachRemaining(System.out::print);
    // xdoc section -- end
  }
}
