/*
Indentation
arrayInitIndent = (default)4
basicOffset = (default)4
braceAdjustment = (default)0
caseIndent = (default)4
forceStrictCondition = true
lineWrappingIndentation = (default)4
throwsIndent = (default)4

*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

public class InputIndentationNestedCallArgumentsInvalid {
    void invalid() {
        call(
            call(
              "under" // violation 'incorrect indentation level'
            )
        );
        call(
            call(
                  "over" // violation 'incorrect indentation level'
            )
        );
        call(
            call(
                "value"
          ) // violation 'incorrect indentation level'
        );
        call(
            call(
                "value"
              ) // violation 'incorrect indentation level'
        );
        call(
          call( // violation 'incorrect indentation level'
                "value"
            )
        );
        call(
            call(
                "value"
            ),
          "other" // violation 'incorrect indentation level'
        );
        call(call(
          "same line" // violation 'incorrect indentation level'
        ));
    }

    private String call(String... values) {
        return null;
    }
}
