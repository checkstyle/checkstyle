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
  int[] numbersGood = new int[10];
  String[] dataGood = {"a", "b"};
  byte bufferGood[];
  int[] matrixGood[];

  void processArray(int[] arr) {
    int valueGood = arr[0];
  }

  void calculateGood(int values[]) {
  }

  void processData(int[] values, String[] labels) {
    for (int i = 0; i < values.length; i++) {
      values[i] = values[i] * 2;
    }
  }

  void initializeData() {
    String[] namesGood = new String[5];
    int[][] gridGood = new int[3][4];
    int[][] correctGrid = new int[5][5];
    correctGrid[0][0] = 1;
  }
}
// xdoc section -- end
