/*
 * InputInvalidArrayInitIndent.java
 *
 * Created on December 9, 2002, 9:57 PM
 */

package com.puppycrawl.tools.checkstyle.indentation;

/**
 *
 * @author  jrichard
 */
public class InputInvalidArrayInitIndent {
    
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
