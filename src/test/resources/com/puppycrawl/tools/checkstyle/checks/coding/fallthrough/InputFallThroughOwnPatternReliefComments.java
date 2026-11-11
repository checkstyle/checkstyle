/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = Continue with next case


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughOwnPatternReliefComments
{
   /* Test relief comment. */
   void methodFallThruCC(int i, int j, boolean cond) {
      while (true) {
          switch (i){
          case 0:
              i++; // fallthru

          case 1: // violation 'Fall\ through from previous branch of the switch statement.'
              i++;
          // fallthru
          case 2: { // violation 'Fall\ through from previous branch of the switch statement.'
              i++;
          }
          // fallthru
          case 3: // violation 'Fall\ through from previous branch of the switch statement.'
              i++;
          /* fallthru */case 4: // violation 'Fall\ through from prev.* br.* switch statement.'
                break;
          case 5:
              i++;
          // fallthru
          }
      }
   }

   /* Like above, but C-style comments. */
   void methodFallThruC(int i, int j, boolean cond) {
      while (true) {
          switch (i){
          case 0:
              i++; /* fallthru */

          case 1: // violation 'Fall\ through from previous branch of the switch statement.'
              i++;
          /* fallthru */
          case 2: // violation 'Fall\ through from previous branch of the switch statement.'
              i++;
          /* fallthru */case 3: // violation 'Fall\ through from prev.* br.* switch statement.'
                break;
          case 4:
              i++;
          /* fallthru */
          }
      }
   }

   /* Like above, but C-style comments with no spaces. */
   void methodFallThruC2(int i, int j, boolean cond) {
      while (true) {
          switch (i){
          case 0:
              i++; /*fallthru*/

          case 1: // violation 'Fall\ through from previous branch of the switch statement.'
              i++;
          /*fallthru*/
          case 2: // violation 'Fall\ through from previous branch of the switch statement.'
              i++;
          /*fallthru*/case 3: // violation 'Fall\ through from prev.* br.* switch statement.'
                break;
          case 4:
              i++;
          /*fallthru*/
          }
      }
   }

   /* C-style comments with other default fallthru-comment. */
   void methodFallThruCOtherWords(int i, int j, boolean cond) {
      while (true) {
          switch (i){
          case 0:
              i++; /* falls through */

          case 1: // violation 'Fall\ through from previous branch of the switch statement.'
              i++;
          /* falls through */
          case 2: // violation 'Fall\ through from previous branch of the switch statement.'
              i++;
          /* falls through */case 3: // violation 'Fall\ through from prev.* br.* switch statement.'
                break;
          case 4:
              i++;
          /* falls through */
          }
      }
   }
}
