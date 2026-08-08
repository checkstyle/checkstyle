/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ArrayBracketWhitespace"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketwhitespace;
// xdoc section - start
class Example1 {
  int[] arr1;
  int[][] arr2;

  void method(int[] p) {
    int i = p[0];
    int j = p [0]; // violation ''\[' is preceded with whitespace.'
    int k = p[ 0]; // violation ''\[' is followed by whitespace.'
    int x = p[0]++;

    int[] another = new int[5];

    int a = p[1];
    int b = p [1]; // violation ''\[' is preceded with whitespace.'
    int c = p[ 1]; // violation ''\[' is followed by whitespace.'
    int d = p[1 ]; // violation ''\]' is preceded with whitespace.'
    p[0]++;
    p[0] += 1;
    p[0]+= 1; // violation ''\]' is not followed by whitespace.'
  }
}
// xdoc section - end
