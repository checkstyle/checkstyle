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
  int foo = 5;
  int[][] array = { { 1, 2 }
                    , { 3, 4 } };
  int[][] array2 = { { 1, 2 },
                       { 3, 4 } };
  void ellipsisExample(String ...params) {}
  // violation above ''...' is preceded with whitespace'
  void ellipsisExample2(String
                            ...params) {

    Lists.charactersOf("foo")
        .listIterator()
        .forEachRemaining(System.out::print);

  }

  void example() {
    foo ++; // violation 'is preceded with whitespace'
    foo++;
    for (int i = 0 ; i < 5; i++) {} // violation '';' is preceded with whitespace'
    for (int i = 0; i < 5; i++) {}
    Lists.charactersOf("foo").listIterator()
        .forEachRemaining(System.out::print);

    Lists.charactersOf("foo").listIterator().forEachRemaining(System.out ::print);

    Lists.charactersOf("foo").listIterator().forEachRemaining(System.out::print);
    Lists.charactersOf("foo").listIterator()
        .forEachRemaining(System.out::print)

    ;
    {
      label1 : // violation '':' is preceded with whitespace'
      for (int i = 0; i < 10; i++) {}
    }
    {
      label2:
      while (foo < 5) {}
    }
  }
}
// xdoc section -- end
