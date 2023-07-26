/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughRough {

   void methodFallThruCCustomWords(int i, int j, boolean cond) {
      while (true) {
          switch (i){
          case 0:
              i++; /* Continue with next case */

          case 1: // violation 'f'
              i++;
          /* Continue with next case */
          case 2:
              i++;
          /* Continue with next case */case 3:
                break;
          case 4:
              i++;
          /* Continue with next case */
          }
      }
   }
}
