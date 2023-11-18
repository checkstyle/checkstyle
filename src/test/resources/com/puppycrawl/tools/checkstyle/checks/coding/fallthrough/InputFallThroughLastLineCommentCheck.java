/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughLastLineCommentCheck {

    public void method(int i) {
        switch (i) {
            case 1:
                if (true) {
                }
                // comment
                // fall through
                // check
            case 2: // violation 'Fall\ through from previous branch of the switch statement'
                break;
            case 3:
                break;
        }
    }

    public void method2(int i) {
        switch (i) {
            case 1:
                i++;
                /* block */ /* fallthru */ // comment
            case 2:
                // this is comment
                i++;
                // fall through
        }
    }

    public void method3(int i) {
        switch (i) {
            case 1:
                break;
            case 2:
                // this is comment
                i++;
                // fallthru
        }
    }

    public void method4(int i) {
        switch (i) {
            case 1:
                break;
            case 2:
                // this is comment
                i++;
            /* comment */ // fallthru
        }
    }

   void methodFallThruCC(int i) {
      while (true) {
          switch (i){
          case 5:
              i++;
          // fallthru
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
              /* block */ // comment
              /* fallthru */
          }
      }
   }

    void method6(String str) {
        switch (str) {
            case "9": String s = "s🥳d🥳s";
                //🥳d🥳 fallthru

            case "10": String s2 = "s🥳d🥳s";
            /*🥳🥳🥳🥳🥳🥳*/ /* fallthru */ default: str.toUpperCase();
            // violation above 'Fall .* from the last branch of the switch statement'
        }
    }

   void method7(int i, int j, boolean cond) {
      while (true) {
          switch (i){
          case 5:
              i++;
              /* block */ i++; /* fallthru */ // comment
          }
      }
   }
}
