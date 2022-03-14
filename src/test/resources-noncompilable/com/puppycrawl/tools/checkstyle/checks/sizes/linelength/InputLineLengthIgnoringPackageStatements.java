/*
LineLength
fileExtensions = (default)all files
ignorePattern = ^package.*
max = 75


*/
//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.   checkstyle.checks.sizes.linelength; // ok

// violation below 'longer than 75 characters (found 76)'
import java.   security.    interfaces.          RSAMultiPrimePrivateCrtKey;

import java.util.Arrays; // ok

public class InputLineLengthIgnoringPackageStatements {
    @Override
    public String toString() {

        // violation below 'longer than 75 characters (found 86)'
        String s = "package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;";

        // violation 2 lines below 'longer than 75 characters (found 76)'
        String a = """
            package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;
            """;

        String b = """
package com.puppycrawl.tools.checkstyle.    checks.sizes.     linelength;
            """; // ok above

        // violation below 'longer than 75 characters (found 77)'
        return "This line is longer than 60 characters and should be logged";
    }
}
