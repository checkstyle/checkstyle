/*
WhitespaceAfter
tokens = LITERAL_TRY


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

import java.io.IOException;
import java.io.InputStream;

public class InputWhitespaceAfterLiteralTry {

        public static void main(String[] args) throws IOException {
            // violation below ''try' is not followed by whitespace'
            try(InputStream ignored = System.in) {
            }
        }
}
