/*
LineLength
fileExtensions = (default)all files
ignorePattern = ^import.*
max = 60


*/

// violation below
package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;

import com.puppycrawl.tools.checkstyle.grammar.comments.InputFullOfSinglelineComments;
import static com.puppycrawl.tools.checkstyle.grammar.comments.InputFullOfSinglelineComments.main;

public class InputLineLengthValidatePackageStatements {
    @Override
    public String toString() {
        // violation below
        return "This line is longer than 60 characters and should be logged";
    }
}
