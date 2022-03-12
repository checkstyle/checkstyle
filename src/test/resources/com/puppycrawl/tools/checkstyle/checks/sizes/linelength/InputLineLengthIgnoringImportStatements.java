/*
LineLength
fileExtensions = (default)all files
ignorePattern = (import|//).*
max = 58


*/

// violation below 'longer than 58 characters (found 71)'
       package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;

         import java.security.interfaces.RSAMultiPrimePrivateCrtKey; // ok
import java.nio.channels.spi.AbstractInterruptibleChannel; // ok

public class InputLineLengthIgnoringImportStatements {
    @Override
    public String toString() {
        // violation below 'longer than 58 characters (found 77)'
        return "This line is longer than 60 characters and should be logged";
    }
}
