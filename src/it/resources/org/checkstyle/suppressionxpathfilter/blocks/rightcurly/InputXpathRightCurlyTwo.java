/*
RightCurly
option = alone
tokens = (default)LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE


*/

package org.checkstyle.suppressionxpathfilter.blocks.rightcurly;

import java.io.BufferedReader;
import java.io.IOException;

public class InputXpathRightCurlyTwo {
    public void fooMethod() throws IOException {
        try (BufferedReader br1 = new BufferedReader(null)) {
            ; } // violation ''}' at column 15 should be alone on a line.'
    }
}
