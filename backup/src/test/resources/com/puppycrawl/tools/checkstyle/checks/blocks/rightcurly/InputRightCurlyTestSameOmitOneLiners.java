/*
RightCurly
option = (default)SAME
tokens = (default)LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

/*
 * @see https://github.com/checkstyle/checkstyle/issues/1416
 * @author <a href="mailto:piotr.listkiewicz@gmail.com">liscju</a>
 */
public class InputRightCurlyTestSameOmitOneLiners {
    public static void main(String[] args) {
        boolean after = false;
        try {
        } finally { after = true; } // ok
    }
}
