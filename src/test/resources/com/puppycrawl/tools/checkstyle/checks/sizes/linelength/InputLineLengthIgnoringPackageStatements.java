/*
LineLength
fileExtensions = (default)all files
ignorePattern = (package|//).*
max = 58


*/
          package com.puppycrawl.tools.checkstyle.checks.sizes.linelength; // ok

// violation below 'longer than 58 characters (found 59)'
import java.security.interfaces.RSAMultiPrimePrivateCrtKey;

import java.util.Arrays; // ok

// violation below 'longer than 58 characters (found 77)'
                  import java.security.interfaces.RSAMultiPrimePrivateCrtKey;

public class InputLineLengthIgnoringPackageStatements {
    @Override
    public String toString() {

        // violation below 'longer than 58 characters (found 77)'
        return "This line is longer than 60 characters and should be logged";
    }
}
