package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;

import com.puppycrawl.tools.checkstyle.grammars.comments.InputFullOfSinglelineComments;
import static com.puppycrawl.tools.checkstyle.grammars.comments.InputFullOfSinglelineComments.main;

public class InputLongImportStatements {
    @Override
    public String toString() {
        return "This is very long line that should be logged because it is not import";
    }
}
