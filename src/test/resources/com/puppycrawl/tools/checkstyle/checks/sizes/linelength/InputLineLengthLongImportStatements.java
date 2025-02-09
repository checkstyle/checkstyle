/*
LineLength
fileExtensions = (default)
ignorePattern = (default)^(package|import) .*
max = (default)80


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;

import java.util.                                                                 HashMap;
import java.util.                                                                 HashSet;

public class InputLineLengthLongImportStatements {
    @Override
    public String toString() {
        return "This is very long line that should be logged because it is not import"; // violation
    }
}
