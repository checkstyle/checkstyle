/*
OneStatementPerLine
treatTryResourcesAsStatement = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;
import java.io.StringReader;
/*
    This class provides test input for OneStatementPerLineCheck with different
    types of multiline statements.
    A Java statement is the smallest unit that is a complete instruction.
    Statements must end with a semicolon.
    Statements generally contain expressions (expressions have a value).
    One of the simplest is the Assignment Statement.
 */

public class InputOneStatementPerLineMultilineTwo {
    int var1 = 1,var2 = 2;

    /**
     * Multiline for loop statement is legal.
     */
    private void foo3() {
        for(int n = 0,
            k = 1
            ; n<5
            ;
            n++, k--) {}
    }

    /**
     * One statement inside multiline for loop block is legal.
     */
    private void foo4() {
        for(int n = 0,
            k = 1
            ; n<5
            ; ) { int a = 5,
        b = 2;}
    }

    /**
     * Two statements on the same lne
     * inside multiline for loop block are illegal.
     */
    private void foo5() {
        for(int n = 0,
            k = 1
            ; n<5
            ;
            n++, k--) { var1++; var2++; } // violation
    }

    /**
     * Multiple statements within try-with-resource on a separate line is legal.
     * @see <a href="https://github.com/checkstyle/checkstyle/issues/2211">false match</a>
     */
    private void issue2211pass() {
        try(
                AutoCloseable i = new java.io.StringReader("");
                AutoCloseable k = new java.io.StringReader("");
        ) {
        } catch (Exception e1) {
        }
    }

    /**
     * Multiple statements within try-with-resource on a separate line is legal. Per PR comment:
     * @see <a href="https://github.com/checkstyle/checkstyle/pull/2750#issuecomment-166032327"/>
     */
    private void issue2211pass2() {
        try( AutoCloseable i = new java.io.StringReader("");
            AutoCloseable k = new java.io.StringReader("");) {
        } catch (Exception e1) {
        }
    }

    /**
     * Multiple statements within try-with-resource on next line after try is illegal.
     * @see <a href="https://github.com/checkstyle/checkstyle/issues/2211">false match</a>
     */
  private void issue2211fail() {
    try(
  AutoCloseable i=new java.io.PipedReader();AutoCloseable k=new java.io.PipedReader();// violation
    ) {
    } catch (Exception e1) {
    }
  }

    /**
     * Multiple statements within try-with-resource on a same line as try is illegal. PR comment:
     * @see <a href="https://github.com/checkstyle/checkstyle/pull/2750#issuecomment-166032327"/>
     */
  private void issue2211fail2() {
    try(AutoCloseable i=new StringReader("");AutoCloseable k=new StringReader("");) { // violation
    } catch (Exception e1) {
    }
  }

}
