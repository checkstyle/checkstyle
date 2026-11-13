package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionFallThroughDefault5 {
    void synchronizedStatement() {
       switch (hashCode()) {
           case 1:
               synchronized (this) {
                   break;
               }
           case 2:
               // synchronized nested in if
               if (true) {
                   synchronized (this) {
                       break;
                   }
               } else {
                   synchronized (this) {
                       break;
                   }
               }
           case 3:
               synchronized (this) {
               }
               // fallthru
           default:
               break;
       }
    }

    void multipleCasesOnOneLine() {
        int i = 0;
        switch (i) {
        case 0: case 1: i *= i; // fall through
        case 2: case 3: i *= i; // fall through
        case 4: case 5: i *= i; // fall through
        case 6: case 7: i *= i;
            break;
        default:
            throw new RuntimeException();
        }
    }

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
              case 13: //warn
                  i++; // fall+through
              case 14: //warn
                  i++; // falls_thru
              case 15: //warn
                  i++; // falls=through
              case 16: //warn
                  i++; // falls-throug
              default: //warn
                  throw new RuntimeException();
          }
      }
   }
}
