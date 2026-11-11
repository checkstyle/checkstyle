/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughLastCaseGroupNestedSwitch
{
    void method(int i, int j, boolean cond) {
        while (true) {
            switch (i) {
            case 23:
                switch (j) {
                case 1:
                    continue;
                case 2:
                    return;
                default:
                    return;
                }
            case 24:
                switch (j) {
                case 1:
                    continue;
                case 2:
                    break;
                default:
                    return;
                }
            default:
            // 2 violations above:
            //    'Fall through from previous branch of the switch statement.'
            //    'Fall through from the last branch of the switch statement.'
                // this is the last label
                i++;
            }
        }
    }

    /* Like above, but all fall throughs with relief comment */
    void methodFallThru(int i, int j, boolean cond) {
      while (true) {
          switch (i) {
          case 23:
              switch (j) {
              case 1:
                  continue;
              case 2:
                  return;
              default:
                  return;
              }
          case 24:
              i++;
          /* fallthru */ case 25:
              i++;
              break;

          case 26:
              switch (j) {
              case 1:
                  continue;
              case 2:
                  break;
              default:
                  return;
              }
              // fallthru
          default:
              // this is the last label
              i++;
          // fallthru
         }
      }
   }

    void method1472228(int i) {
        switch(i) {
        case 2:
            // do nothing
            break;
        default:
        }
    }

    void nestedSwitches() {
        switch (hashCode()) {
            case 1:
                switch (hashCode()) { // causing NullPointerException in the past
                    case 1:
                }
            default: // violation 'Fall\ through from previous branch of the switch statement.'
        }
    }

    void nextedSwitches2() {
        switch(hashCode()) {
        case 1:
            switch(hashCode()){}
        case 2: // violation 'Fall\ through from previous branch of the switch statement.'
            System.lineSeparator();
            break;
        }
    }
}
