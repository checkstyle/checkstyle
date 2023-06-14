/*
NoWhitespaceBefore
allowLineBreaks = true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

import com.google.common.collect.Lists;

class Example2 {
  // xdoc section -- start
  int[][] array = { { 1, 2 }
                  , { 3, 4 } };
  int[][] array2 = { { 1, 2 },
                     { 3, 4 } };
  void ellipsisExample(String ...params) {};
  // violation above ''...' is preceded with whitespace'
  void ellipsisExample2(String
                          ...params) {
    Lists.charactersOf("foo")
         .listIterator()
         .forEachRemaining(System.out::print);
  };
  // xdoc section -- end
}
