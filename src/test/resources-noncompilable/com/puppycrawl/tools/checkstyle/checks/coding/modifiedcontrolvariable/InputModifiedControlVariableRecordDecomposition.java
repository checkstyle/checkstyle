/*
ModifiedControlVariable
skipEnhancedForLoopVariable = (default)false
tokens = (default)OBJBLOCK, LITERAL_FOR, FOR_ITERATOR, FOR_EACH_CLAUSE, ASSIGN, \
         PLUS_ASSIGN, MINUS_ASSIGN, STAR_ASSIGN, DIV_ASSIGN, MOD_ASSIGN, SR_ASSIGN, \
         BSR_ASSIGN, SL_ASSIGN, BAND_ASSIGN, BXOR_ASSIGN, BOR_ASSIGN, INC, \
         POST_INC, DEC, POST_DEC


*/

// non-compiled with javac: compiling on jdk before Java21 (java20)
package com.puppycrawl.tools.checkstyle.checks.coding.modifiedcontrolvariable;

import java.util.List;

public class InputModifiedControlVariableRecordDecomposition {
    static void m() {
        List<Point> points =
                List.of(new Point(1, 2), new Point(3, 4));

        // record decomposition in enhanced for loop
        for (Point(var x, var y): points) {}
        for (Point(Integer x, Integer y): points) {}

        // equivalent to above, except that we have a Point p
        for (Point p : points) {
            var x = p.x();
            var y = p.y();
        }

        // similar to above, except that we have a Point p
        // that is 'modified' in the loop body (by existing check logic)
        for (Point p : points) {
            var x = p.x();
            var y = p.y();
            p = new Point(1, 2); // violation, 'Control variable 'p' is modified.'
        }

        // Record decomposition in enhanced for loop with reassigned parameters. Since we
        // iterate over a list of records, and we have no access to the records themselves,
        // we cannot 'modify' the control variable. The following should be allowed
        // by this check. We can't do 'for (Point(var x, var y) p: points)', this will
        // not compile.
        for (Point(var x, var y): points) {
            x = 1;
            y = 2;
        }
    }

    public static void main(String[] args) {
        m();
    }

    record Point(Integer x, Integer y) { }
}
