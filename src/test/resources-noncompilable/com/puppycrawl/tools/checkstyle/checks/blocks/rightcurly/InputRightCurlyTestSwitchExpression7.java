/*
RightCurly
option = ALONE
tokens = LITERAL_SWITCH

*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestSwitchExpression7 {

      String isDayNameLong(Day day) {
          return switch (day) {
              case MON, FRI, SUN -> 6;
              case TUE -> 7;
              case THU, SAT -> 8;
              case WED -> 9;
          } > 7 ? "long" : "short";
      }

     int arithmetic(Day day) {
         return switch (day) {
             case MON, FRI, SUN -> 6;
             case TUE -> 7;
             case THU, SAT -> 8;
             case WED -> 9;
         } % 2;
     }

     int signArithmetic(Day day) {
         return -switch (day) {
             case MON, FRI, SUN -> 6;
             case TUE -> 7;
             case THU, SAT -> 8;
             case WED -> 9;
         };
     }

     int usedOnBothSidesOfArithmeticExpression(Day day) {
         return switch (day) {
             case MON, TUE -> 0;
             case WED -> 1;
            default -> 2;
         } * switch (day) {
             case WED, THU -> 3;
             case FRI -> 4;
             default -> 5;
         };
     }

     static {
         int i = 0;
         int dummy = 1 + switch (i) {
             case -1: yield 1;
             default:
                 i++;
                 yield 1;
         };
         if (i != 1) {
             throw new IllegalStateException("Side effects missing.");
         }
     }

     static {
         int x;

         int t7 = new DefiniteAssignment1().id(switch (0) {
             default -> isTrue();
         } && (x = 1) == 1 && x == 1 ? 2 : -1);

         if (t7 != 2) {
             throw new IllegalStateException("Unexpected result.");
         }
     }

    private static boolean isTrue() {
          return true;
    }
}

class DefiniteAssignment1 {
    int id(int id) {
        return id;
    }
}

enum Day {
    MON, TUE, WED, THU, FRI, SAT, SUN
}
