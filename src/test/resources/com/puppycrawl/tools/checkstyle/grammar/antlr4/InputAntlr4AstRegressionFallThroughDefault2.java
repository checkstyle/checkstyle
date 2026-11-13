package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionFallThroughDefault2 {
    /* Like above, but all fall throughs with relief comment */
    void methodFallThru(int i, int j, boolean cond) {
      while (true) {
          switch (i) {
          case -1: // FALLTHRU

          case 0: // no problem
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
          case 14:
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
