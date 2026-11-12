/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughDefaultLoops
{
    void method(int i, int j, boolean cond) {
        while (true) {
            switch (i) {
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
            case 15: // violation 'Fall\ through from previous branch of the switch statement.'
                do {
                    System.identityHashCode("something");
                    return;
                } while(true);
            case 16:
                for (int j1 = 0; j1 < 10; j1++) {
                    String.valueOf("something");
                    return;
                }
            case 17:
                while (true)
                    throw new RuntimeException("");
            }
        }
    }

    /* Like above, but all fall throughs with relief comment */
    void methodFallThru(int i, int j, boolean cond) {
      while (true) {
          switch (i) {
          case 16:
              for (int j1 = 0; j1 < 10; j1++) {
                  String.valueOf("something");
                  return;
              }
          case 17:
              while (cond)
                  throw new RuntimeException("");
          case 18:
              while(cond) {
                  break;
              }
              // fallthru
          case 19:
              try {
                  i++;
                  break;
              } catch (RuntimeException e) {
                  break;
              } catch (Error e) {
                  return;
              }
          case 20:
              try {
                  i++;
                  break;
              } catch (RuntimeException e) {
              } catch (Error e) {
                  return;
              }
              // fallthru
          case 21:
              try {
                  i++;
              } catch (RuntimeException e) {
                  i--;
              } finally {
                  break;
              }
          case 22:
              try {
                  i++;
                  break;
              } catch (RuntimeException e) {
                  i--;
                  break;
              } finally {
                  i++;
              }
         }
      }
   }
}
