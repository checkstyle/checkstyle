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
  int[] numbersWarn = new int[10 ]; // violation, ']' is preceded with whitespace
  int[] numbersGood = new int[10];

  String [] dataWarn = {"a", "b"}; // violation, '[' is preceded with whitespace
  String[] dataGood = {"a", "b"};

  byte bufferWarn[ ];
  // 2 violations above
  // '[' is followed by whitespace
  // ']' is preceded with whitespace
  byte bufferGood[];

  int[] matrixWarn[] ; // violation, ']' is followed by whitespace
  int[] matrixGood[];

  void processArray(int[] arr) {
    int value = arr [0]; // violation, '[' is preceded with whitespace
    int valueGood = arr[0];
  }

  void calculateWarn(int values[ ]) {
    // 2 violations above
    // '[' is followed by whitespace
    // ']' is preceded with whitespace
  }
  void calculateGood(int values[]) {
  }
  void processData(int[] values, String[] labels) {
    for (int i = 0; i < values.length; i++) {
      values[i] = values[i] * 2;
    }
  }

  void initializeData() {
    String[] namesWarn = new String[ 5 ];
    // 2 violations above
    // '[' is followed by whitespace
    // ']' is preceded with whitespace
    String[] namesGood = new String[5];

    int[][] gridWarn = new int[3 ][4]; // violation, ']' is preceded with whitespace
    int[][] gridGood = new int[3][4];
    int[][] correctGrid = new int[5][5];
    correctGrid[0][0] = 1;
  }
}
// xdoc section -- end
