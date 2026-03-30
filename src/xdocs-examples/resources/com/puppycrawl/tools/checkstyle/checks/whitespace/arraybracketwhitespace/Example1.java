/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ArrayBracketWhitespace"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketwhitespace;

// xdoc section -- start
class Example1 {
  int[] arr1; // ok, valid syntax
  int[][] arr2; // ok, valid syntax

  void method(int[] p) {
    int i = p[0]; // ok, valid syntax
    int j = p [0]; // violation, '[' is preceded with whitespace
    int k = p[ 0]; // violation, '[' is followed by whitespace
    int x = p[0]++; // ok, ']' followed by postfix operator

    int[] another = new int[5]; // ok, valid syntax

    int a = p [1]; // violation, '[' preceded with whitespace
    int b = p[ 1]; // violation, '[' followed by whitespace
    int c = p[1 ]; // violation, ']' preceded with whitespace
    p[0]++; // ok, valid syntax
    p[0]+= 1; // violation, ']' not followed by whitespace
  }
}
// xdoc section -- end
