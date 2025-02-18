/*
LineLength
fileExtensions = (default)
ignorePattern = ^import.*
max = 75


*/
//non-compiled with javac: Compilable with Java17

// violation below 'longer than 75 characters (found 79)'
package com.       puppycrawl.tools.    checkstyle.checks.    sizes.linelength;

import java.   security.    interfaces.          RSAMultiPrimePrivateCrtKey;

public class InputLineLengthIgnoringImportStatements {
    @Override
    public String toString() {

        // violation below 'longer than 75 characters (found 81)'
        String s = "import java.security.interfaces.RSAMultiPrimePrivateCrtKey;";

        // violation 2 lines below 'longer than 75 characters (found 84)'
        String a = """
            import java.security.       interfaces.      RSAMultiPrimePrivateCrtKey;
            """;

        String b = """
import java.          security.          interfaces.    RSAMultiPrimePrivateCrtKey;
            """; // ok above

        // violation below 'longer than 75 characters (found 77)'
        return "This line is longer than 60 characters and should be logged";
    }
}
