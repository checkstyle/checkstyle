/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughDefaultReliefWithDash
{
   /* Test relief comment. */
   void methodFallThruWithDash(int i, int j, boolean cond) {
      while (true) {
          switch (i){
              case 0:
                  i++; // fallthru
              case 1:
                  i++; // fall thru
              case 2:
                  i++; // fall-thru
              case 3:
                  i++; // fallthrough
              case 4:
                  i++; // fall through
              case 5:
                  i++; // fall-through
              case 6:
                  i++; // fallsthru
              case 7:
                  i++; // falls thru
              case 8:
                  i++; // falls-thru
              case 9:
                  i++; // fallsthrough
              case 10:
                  i++; // falls through
              case 11:
                  i++; // falls-through
              case 12:
                  i++; // fall--through
              case 13: // violation 'Fall\ through from previous branch of the switch statement.'
                  i++; // fall+through
              case 14: // violation 'Fall\ through from previous branch of the switch statement.'
                  i++; // falls_thru
              case 15: // violation 'Fall\ through from previous branch of the switch statement.'
                  i++; // falls=through
              case 16: // violation 'Fall\ through from previous branch of the switch statement.'
                  i++; // falls-throug
              default: // violation 'Fall\ through from previous branch of the switch statement.'
                  throw new RuntimeException();
          }
      }
   }
}
