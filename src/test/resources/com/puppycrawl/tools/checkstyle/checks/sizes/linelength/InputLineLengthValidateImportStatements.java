/*
LineLength
fileExtensions = (default)all files
ignorePattern = ^package.*
max = 60


*/
package com.puppycrawl.tools.checkstyle.checks.sizes.linelength; // ok

// violation below
import com.puppycrawl.tools.checkstyle.grammar.comments.InputFullOfSinglelineComments;

// violation below
import static com.puppycrawl.tools.checkstyle.grammar.comments.InputFullOfSinglelineComments.main;

// violation below
         import com.puppycrawl.tools.checkstyle.StatelessCheck;

public class InputLineLengthValidateImportStatements {
    @Override
    public String toString() {
        // violation below
        return "This line is longer than 60 characters and should be logged";
    }
}
