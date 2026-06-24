/*
RightCurly
option = (default)SAME
tokens = (default)LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestTryCatchIfElse2 {
      /** some javadoc. */
    public static void main(String[] args) {
        boolean after = false;

        try {
            /* foo */
        } catch (NullPointerException e) {
            /* foo */
        } catch (Exception e) {
            /* foo */
        } finally {
            after = true;
        }

        if (after) {
            System.out.println("after");
        } else if (after) {
            System.out.println("before");
        } else if (after) {
             System.out.println("before");
        } else {
            System.out.println("before");
        }

        if (after) {
             System.out.println("after");
             // violation below ''}' at column 28 should be alone on a line'
        } else if (after) {} else if (after) {
             System.out.println("before");
             // violation below ''}' at column 40 should be alone on a line'
        } else if (!after) { /* foo */ } else {
             System.out.println("before");
        }
    }
}
