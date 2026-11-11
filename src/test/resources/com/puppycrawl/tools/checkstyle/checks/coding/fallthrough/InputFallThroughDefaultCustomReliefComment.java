/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughDefaultCustomReliefComment
{
   /* C-style comments with custom fallthru-comment. */
   void methodFallThruCCustomWords(int i, int j, boolean cond) {
      while (true) {
          switch (i){
          case 0:
              i++; /* Continue with next case */

          case 1: // violation 'Fall\ through from previous branch of the switch statement.'
              i++;
          /* Continue with next case */
          case 2: // violation 'Fall\ through from previous branch of the switch statement.'
              i++;
          /* Continue.* */case 3: // violation 'Fall\ through from previous branch.* switch.*'
                break;
          case 4:
              i++;
          /* Continue with next case */
          }
      }
   }

   void methodFallThruLastCaseGroup(int i, int j, boolean cond) {
       while (true) {
           switch (i){
           case 0:
               i++; // fallthru
           }
           switch (i){
           case 0:
               i++;
               // fallthru
           }
           switch (i){
           case 0:
               i++;
           /* fallthru */ }
       }
    }
}
