/*
WhitespaceAfter
tokens = LITERAL_TRY


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

import java.io.IOException;
import java.io.InputStream;

public class InputWhitespaceAfterLiteralTry {
    public static void main(String[] args) throws IOException {
        try (InputStream ignored = System.in) {}

        try
            (InputStream ignored = System.in) {}

        try(InputStream ignored = System.in) {} // violation ''try' is not followed by whitespace'

        try {}catch (Exception e){}

        try{}catch (Exception e){} // violation ''try' is not followed by whitespace'
    }
}
