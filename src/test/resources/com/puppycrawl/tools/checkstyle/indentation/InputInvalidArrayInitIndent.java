package com.puppycrawl.tools.checkstyle.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputInvalidArrayInitIndent { //indent:0 exp:0

    int[] array = new int[] {1, 2, 3};

  int[] arrayb = new int[] {1, 2, 3};
      int[] arrayc = new int[] {1, 2, 3};

  int[] array2 = new int[] {
      1, 2, 3
      };

      int[] array2b = new int[] {
        1, 2, 3
    };

    int[] array3 = new int[] {
         1,
       2,
         3
    };


    int[] array4 = new int[]
  {
        1,
        2,
        3
      };


    int[] array5 = new int[]
  {1, 2, 3};


    int[] array6 = new int[] { 1, 2,
                    3,
    4,};



    int[] array7 = new int[] {
      1, 2,
        3
    };


  int[] array8 = new int[] { };

      int[] array9 = new int[] {
  };

    int[][] array10 = new int[][] {
      new int[] { 1, 2, 3},
        new int[] { 1, 2, 3},
    };


    int[][] array10b
        = new int[][] {
          new int[] { 1, 2, 3},
            new int[] { 1, 2, 3},
        };

    private void func1(int[] arg) {

    }


    /** Creates a new instance of InputValidArrayInitIndent */
    public InputInvalidArrayInitIndent() {

        func1(new int[] {
        1, 2, 3
        });
    }

    private static char[] sHexChars = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private void myFunc3()
    {
        char[] sHexChars2 = {
          '0', '1', '2', '3', '4', '5', '6', '7',
              '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        char[] sHexChars3 = {
          '0', '1', '2', '3', '4', '5', '6', '7',
              '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
      };

        char[] sHexChars4 =
      {
              '0', '1', '2', '3', '4', '5', '6', '7',
          '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
      };

    }

}
