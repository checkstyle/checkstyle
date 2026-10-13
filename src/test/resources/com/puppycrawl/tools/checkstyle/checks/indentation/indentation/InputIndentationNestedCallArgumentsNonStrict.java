/*
Indentation
arrayInitIndent = (default)4
basicOffset = (default)4
braceAdjustment = (default)0
caseIndent = (default)4
forceStrictCondition = (default)false
lineWrappingIndentation = (default)4
throwsIndent = (default)4

*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

public class InputIndentationNestedCallArgumentsNonStrict {
    void valid() {
        call(
            call(
            "value"
        ));
        call(
            call(
                "value"
            )
        );
    }

    private String call(String... values) {
        return null;
    }
}
