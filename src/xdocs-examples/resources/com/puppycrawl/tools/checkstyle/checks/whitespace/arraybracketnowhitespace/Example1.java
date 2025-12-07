/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ArrayBracketNoWhitespace"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketnowhitespace;

// xdoc section -- start
class Example1 {
    int array1[] ; // violation, space before ;
    int array2[ ] = new int[ 5 ]; // 3 violations, space in [] and before ;
    int[] array3 = new int[5 ]; // violation, space before ]
    int array4 [] = new int [ 5 ] ; // 5 violations, space after variable name, in all []
    int array5
      [ ]
      =
      new int
      [ 1 ] ; // violations, space in []
    void method1(int param[ ]) { // violation, space in []
    }
    void method2() {
        int arr [  ] = { 1, 2, 3 }; // violations, spaces in []
    }
}
// xdoc section -- end
