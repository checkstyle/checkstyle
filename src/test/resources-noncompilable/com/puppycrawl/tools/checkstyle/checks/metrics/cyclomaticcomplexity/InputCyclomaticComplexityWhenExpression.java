/*
CyclomaticComplexity
max = 0
switchBlockAsSingleDecisionPoint = (default)false
tokens = (default)LITERAL_WHILE,LITERAL_DO,LITERAL_FOR,LITERAL_IF,LITERAL_SWITCH,LITERAL_CASE, \
         LITERAL_CATCH,QUESTION,LAND,LOR,LITERAL_WHEN

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.metrics.cyclomaticcomplexity;

public class InputCyclomaticComplexityWhenExpression {

   // violation below, 'Cyclomatic Complexity is 5 (max allowed is 0)'
   void testIf(Object o) {   // 1, function declaration
      if (o instanceof Integer i && i == 9) {}  // 2, if - 3, &&
      else if (o instanceof String s && s.length() == 9) {} // 4, if - 5, &&
   }

   // violation below, 'Cyclomatic Complexity is 5 (max allowed is 0)'
   void testSwitch(Object o) {   // 1, function declaration
      switch (o) {
         case Integer i when i == 9 -> {} // 2, case - 3, when
         case String s when s.length() == 9 -> {} // 4, case - 5, when
         default -> {}
      }
   }

   // violation below, 'Cyclomatic Complexity is 7 (max allowed is 0)'
   void testSwitch2(Object o) {  // 1, function declaration
     switch (o) {
         case Integer i -> {}  // 2, case
         case Point(int x, int y) when (x == 0 && y == 0) -> {} // 3, case - 4, when - 5, &&
         case Point(int x, int y) when x == 0 -> {} // 6, case - 7, when
         default ->  {}
      }
   }

   record Point(int x, int y) {}
}
