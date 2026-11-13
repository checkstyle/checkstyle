package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionFallThroughDefault3 {
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

   /* Test relief comment. */
   void methodFallThruCC(int i, int j, boolean cond) {
      while (true) {
          switch (i){
          case 0:
              i++; // fallthru

          case 1:
              i++;
          // fallthru
          case 2: {
              i++;
          }
          // fallthru
          case 3:
              i++;
          /* fallthru */case 4:
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

          case 1:
              i++;
          /* fallthru */
          case 2:
              i++;
          /* fallthru */case 3:
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

          case 1:
              i++;
          /*fallthru*/
          case 2:
              i++;
          /*fallthru*/case 3:
                break;
          case 4:
              i++;
          /*fallthru*/
          }
      }
   }
}
