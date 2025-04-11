/*xml
<module name="Checker">
  <module name="NoWhitespaceBefore"/>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

import com.google.common.collect.Lists;

// xdoc section -- start
class Example1 {
  int foo = 5;
  void example() {
    foo ++; // violation 'is preceded with whitespace'
     foo++; // violation 'is preceded with whitespace'
    foo++ ; // violation '';' is preceded with whitespace'
    foo++;
    for (int i = 0 ; i < 5; i++) {}  // violation '';' is preceded with whitespace'
    for (int i = 0; i < 5; i++) {}
    int[][] array = { { 1, 2 }
                    , { 3, 4 } }; // violation '',' is preceded with whitespace'
    int[][] array2 = { { 1, 2 },
                       { 3, 4 } };
    Lists.charactersOf("foo").listIterator()
           .forEachRemaining(System.out::print)
           ; // violation '';' is preceded with whitespace'
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
