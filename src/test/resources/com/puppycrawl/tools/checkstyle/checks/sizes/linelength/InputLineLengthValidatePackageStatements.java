package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;
// ^--- Violation

/* Config:
 * validatePackage = true
 * max = 60
 */

import com.puppycrawl.tools.checkstyle.grammar.comments.InputFullOfSinglelineComments; // ok
import static com.puppycrawl.tools.checkstyle.grammar.comments.InputFullOfSinglelineComments.main; // ok

public class InputLineLengthValidatePackageStatements {
    @Override
    public String toString() {
        return "This is very long line that should be logged because alongside validatePackage set to true";
        // ^--- Violation
    }
}
