/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoWhitespaceBefore">
      <property name="allowLineBreaks" value="true"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

import com.google.common.collect.Lists;

// xdoc section -- start
class Example2 {
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
}
// xdoc section -- end
