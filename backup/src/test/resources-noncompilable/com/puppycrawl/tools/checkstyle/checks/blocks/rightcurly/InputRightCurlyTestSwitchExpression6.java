/*
RightCurly
option = ALONE
tokens = LITERAL_SWITCH

*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestSwitchExpression6 {
     public void foo5() {
         int x = 0;
         final T e = T.B;

         final boolean t8 = (switch (e) {
         case A:
             x = 1;
         case B:
         default:
             yield false;
         }) && x == 1; // ok

         {
             final T y = T.A;

             final boolean t9 = (switch (y) {
             case A:
                 x = 1;
                 yield true;
             case B:
                 yield (x = 1) == 1 || true;
             default:
                 yield false;
             }) && x == 1; // ok
         }

         {
             final T y = T.A;

             final int v = switch (y) {
             case A -> x = 0;
             case B -> x = 0;
             case C -> x = 0;
             };

             if (x != 0 || v != 0) {
                 throw new IllegalStateException("Unexpected result.");
             }
        }

         {
             final T y = T.A;

             final boolean tA = (switch (y) {
            case A -> {
                 x = 1;
                 yield true;
             }
             case B -> {
                 x = 1;
                 yield true;
             }
             case C -> {
                 x = 1;
                 yield true;
             }
             }) && x == 1; // ok
         }
     }

      public void bar2(Option option) {
          assert 1 < switch (option) {
              case ONE -> 1;
              case TWO -> 2;
              case THREE -> 3;
          }; // ok
      }

      int usedOnBothSidesOfArithmeticExpression(Day day) {
          return switch (day) {
              case MON, TUE -> 0;
              case WED -> 1;
              default -> 2;
          } * switch (day) {  // ok
              case WED, THU -> 3;
              case FRI -> 4;
              default -> 5;
          };
      }

      static {
          int x = 0;
          T e = T.B;

          boolean t8 = (switch (e) {
              case A
                  :
                  x = 1;
                  yield true;
              case B :
                  yield (x = 1) == 1 || true;
              default
                      :yield false;
          }) && x == 1; // ok
     }
}

enum T {
    A, B, C
}

enum Option {
    ONE, TWO, THREE
}

enum Day {
    MON, TUE, WED, THU, FRI
}
