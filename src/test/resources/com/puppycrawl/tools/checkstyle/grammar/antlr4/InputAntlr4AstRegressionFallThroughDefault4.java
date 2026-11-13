package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionFallThroughDefault4 {
   /* C-style comments with other default fallthru-comment. */
   void methodFallThruCOtherWords(int i, int j, boolean cond) {
      while (true) {
          switch (i){
          case 0:
              i++; /* falls through */

          case 1:
              i++;
          /* falls through */
          case 2:
              i++;
          /* falls through */case 3:
                break;
          case 4:
              i++;
          /* falls through */
          }
      }
   }

   /* C-style comments with custom fallthru-comment. */
   void methodFallThruCCustomWords(int i, int j, boolean cond) {
      while (true) {
          switch (i){
          case 0:
              i++; /* Continue with next case */

          case 1:
              i++;
          /* Continue with next case */
          case 2:
              i++;
          /* Continue with next case */case 3:
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
            default: // violation - no fall through comment
        }
    }

    void nextedSwitches2() {
        switch(hashCode()) {
        case 1:
            switch(hashCode()){}
        case 2:
            System.lineSeparator();
            break;
        }
    }

    void ifWithoutBreak() {
        switch(hashCode()) {
        case 1:
            if (true) {
                System.lineSeparator();
            }
        case 2:
            System.lineSeparator();
            break;
        }
    }

    void noCommentAtTheEnd() {
        switch(hashCode()) {
        case 1: System.lineSeparator();

        case 2:
            System.lineSeparator();
            break;
        }
    }
}
