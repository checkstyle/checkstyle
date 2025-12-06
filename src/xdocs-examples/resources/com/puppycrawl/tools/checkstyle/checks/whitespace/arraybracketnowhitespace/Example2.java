/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ArrayBracketNoWhitespace"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketnowhitespace;

// xdoc section -- start
class Example2 {
    int[] array1;
    int[] array2 = new int[5];
    int[] array3 = new int[]{1, 2, 3};
    void method1(int[] param) {
    }
    void method2() {
        int[] arr = { 1, 2, 3 };
    }
}
// xdoc section -- end
