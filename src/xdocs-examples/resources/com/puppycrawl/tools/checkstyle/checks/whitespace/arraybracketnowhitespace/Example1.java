/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ArrayBracketNoWhitespace"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketnowhitespace;

import java.util.function.IntFunction;

// xdoc section - start
class Example1 {
  int[] numbersGood = new int[10];
  int[] numbersWarn = new int[10 ]; // violation '']' is preceded with whitespace'

  String[] dataGood = {"a", "b"};
  String [] dataWarn = {"a", "b"}; // violation ''[' is preceded with whitespace'

  byte bufferGood[];
  byte bufferWarn[ ];
  // 2 violations above:
  // ''[' is followed by whitespace'
  // '']' is preceded with whitespace'

  int[] matrixGood[];
  int[] matrixWarn[] ; // violation '']' is followed by whitespace'

  void methodGood(int[] arr) {}
  void methodWarn(int[]arr) {} // violation '']' is not followed by whitespace'

  void processArray(int[] arr) {
    int[] goodArr = {arr[0], arr[1]};
    int[] warnArr = {arr[0], arr[1] };// violation '']' is followed by whitespace'

    int valueGood = arr[0] * 2;
    int valueWarn = arr[0]* 2; // violation '']' is not followed by whitespace'

    int good = arr[0] >> 2;
    int warn = arr[0]>>2; // violation '']' is not followed by whitespace'

    arr[0]++;
    arr[0] ++; // violation '']' is followed by whitespace'

    Class<?> goodClass = int[].class;
    Class<?> warnClass = int[] .class; // violation '']' is followed by whitespace.'

    IntFunction<int[]> goodMethodRef = int[]::new;
    IntFunction<int[]> warnMethodRef = int[] ::new;
    // violation above '']' is followed by whitespace.'
    IntFunction<int[] > warnAngleBracket = int[]::new;
    // violation above '']' is followed by whitespace.'
  }

  void goodEllipsis(String[]... params) {}
  void warnEllipsis(String[] ... params) {}
  // violation above '']' is followed by whitespace'
}
// xdoc section - end
