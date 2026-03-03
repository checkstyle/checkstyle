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
  int[] numbersWarn = new int[10 ]; // violation, ']' is preceded with whitespace
  String [] dataWarn = {"a", "b"}; // violation, '[' is preceded with whitespace
  byte bufferWarn[ ];
  // 2 violations above
  // '[' is followed by whitespace
  // ']' is preceded with whitespace
  int[] matrixWarn[] ; // violation, ']' is followed by whitespace

  void processArray(int[] arr) {
    int value = arr [0]; // violation, '[' is preceded with whitespace
  }

  void calculateWarn(int values[ ]) {
    // 2 violations above
    // '[' is followed by whitespace
    // ']' is preceded with whitespace
  }

  void initializeData() {
    String[] namesWarn = new String[ 5 ];
    // 2 violations above
    // '[' is followed by whitespace
    // ']' is preceded with whitespace
    int[][] gridWarn = new int[3 ][4]; // violation, ']' is preceded with whitespace
  }
}
// xdoc section -- end
