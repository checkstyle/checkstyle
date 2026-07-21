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
  int foo = 5;
  int[][] array = { { 1, 2 }
                    , { 3, 4 } };
  int[][] array2 = { { 1, 2 },
                       { 3, 4 } };
  void ellipsisExample(String ...params) {}

  void ellipsisExample2(String
                            ...params) {

    Lists.charactersOf("foo")
        .listIterator() // violation ''.' is preceded with whitespace'
        .forEachRemaining(System.out::print);
    // violation above ''.' is preceded with whitespace'
  }

  void example() {
    foo ++;
    foo++;
    for (int i = 0 ; i < 5; i++) {}
    for (int i = 0; i < 5; i++) {}
    Lists.charactersOf("foo").listIterator()
        .forEachRemaining(System.out::print);
    // violation above ''.' is preceded with whitespace'
    Lists.charactersOf("foo").listIterator().forEachRemaining(System.out ::print);
    // violation above ''::' is preceded with whitespace'
    Lists.charactersOf("foo").listIterator().forEachRemaining(System.out::print);
    Lists.charactersOf("foo").listIterator()
        .forEachRemaining(System.out::print)
    // violation above ''.' is preceded with whitespace'
    ;
    {
      label1 :
      for (int i = 0; i < 10; i++) {}
    }
    {
      label2:
      while (foo < 5) {}
    }
  }
}
// xdoc section -- end
