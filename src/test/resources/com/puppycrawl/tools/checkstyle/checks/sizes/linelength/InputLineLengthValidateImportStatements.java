package com.puppycrawl.tools.checkstyle.checks.sizes.linelength; // ok

/* Config:
 * validateImport = true
 * max = 60
 */

import com.puppycrawl.tools.checkstyle.grammar.comments.InputFullOfSinglelineComments; // Violation
import static com.puppycrawl.tools.checkstyle.grammar.comments.InputFullOfSinglelineComments.main; // Violation

public class InputLineLengthValidateImportStatements {
    @Override
    public String toString() {
        return "This is very long line that should be logged because alongside validateImport set to true";
        // ^--- Violation
    }
}
