/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughDefaultJumpsAndIf
{
    void method(int i, int j, boolean cond) {
        while (true) {
            switch (i) {
            case 0:
            case 1:
                i++;
                break;
            case 2:
                i++;
            case 3: // violation 'Fall\ through from previous branch of the switch statement.'
                i++;
                break;
            case 4:
                return;
            case 5:
                throw new RuntimeException("");
            case 6:
                continue;
            case 7: {
                break;
            }
            case 8: {
                return;
            }
            case 9: {
                throw new RuntimeException("");
            }
            case 10: {
                continue;
            }
            case 11: {
                i++;
            }
            case 12: // violation 'Fall\ through from previous branch of the switch statement.'
                if (false)
                    break;
                else
                    break;
            }
        }
    }

    /* Like above, but all fall throughs with relief comment */
    void methodFallThru(int i, int j, boolean cond) {
      while (true) {
          switch (i) {
          case -1: // FALLTHRU

          case 0:
          case 1:
              i++;
              break;
          case 2:
              i++;
              // fallthru
          case 3:
              i++;
              break;
          case 4:
              return;
          case 5:
              throw new RuntimeException("");
          case 6:
              continue;
          case 7: {
              break;
          }
          case 8: {
              return;
          }
          case 9: {
              throw new RuntimeException("");
          }
          case 10: {
              continue;
          }
          case 11: {
              i++;
          }
          // fallthru
          case 12:
              if (false)
                  break;
              else
                  break;
          case 13:
              if (true) {
                  return;
              }
          case 14: // violation 'Fall\ through from previous branch of the switch statement.'
              if (true) {
                  return;
              } else {
                  //do nothing
              }
              // fallthru
          case 15:
              do {
                  System.identityHashCode("something");
                  return;
              } while(true);
         }
      }
   }
}
