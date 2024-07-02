/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough4 {
    void methodFallThru(int i, int j) {
        while (true) {
            switch (i) {
                case -1: // FALLTHRU
                case 0:
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

   void methodFallThruCC(int i, int j) {
      while (true) {
          switch (i){
          case 5:
              i++;
          // fallthru
          }
      }
   }

   void methodFall(int i, int j) {
      while (true) {
          switch (i){
          case 5: // violation 'Fall .* from the last branch of the switch statement'
              i++;
          }
      }
   }

   void method(int i, int j) {
      while (true) {
          switch (i){
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

   void method2(int i, int j) {
      while (true) {
          switch (i){
          case 2: {
              i++;
          }
          // fallthru
          case 3:
              i++;
          /* fallthru */case 4:
                break;
          case 5: // violation 'Fall .* from the last branch of the switch statement'
              i++;
          }
      }
   }

    void method3(int i, int j, boolean cond) {
      while (true) {
          switch (i){
          case 5:
              i++;
              /* block */ /* fallthru */
          }
      }
   }

   void method4(int i, int j, boolean cond) {
      while (true) {
          switch (i){
          case 5:
              i++;
              /* block */ /* comment */ // fallthru
          }
      }
   }

   void method5(int i, int j, boolean cond) {
      while (true) {
          switch (i){
          case 5:
              i++;
              /* block */ /* fallthru */ // comment
          }
      }
   }
}
