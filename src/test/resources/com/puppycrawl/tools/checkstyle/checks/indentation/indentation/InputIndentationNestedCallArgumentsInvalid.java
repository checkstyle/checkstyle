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
              "under" // violation 'incorrect indentation level 14, expected level should be 16.'
            )
        );
        call(
            call(
                  "over" // violation 'incorrect indentation level 18, expected level should be 16.'
            )
        );
        call(
            call(
                "value"
          ) // violation 'incorrect indentation level 10, expected level should be 12.'
        );
        call(
            call(
                "value"
              ) // violation 'incorrect indentation level 14, expected level should be 12.'
        );
        call(
          call( // violation 'incorrect indentation level 10, expected level should be 12.'
                "value"
            )
        );
        call(
            call(
                "value"
            ),
          "other" // violation 'child .* indentation level 10, expected level should be 12.'
        );
        call(call(
          "same line" // violation 'incorrect indentation level 10, expected level should be 12.'
        ));
    }

    void constructorsAndReceivers() {
        call(
            new String(
              "under" // violation 'incorrect indentation level 14, expected level should be 16.'
              ) // violation 'incorrect indentation level 14, expected level should be 12.'
        );
        call(
            call(
                  "over" // violation 'incorrect indentation level 18, expected level should be 16.'
              ).substring( // violation 'incorrect .* level 14, expected level should be 12.'
                  0 // violation 'incorrect indentation level 18, expected level should be 16.'
            )
        );
    }

    private String call(String... values) {
        return null;
    }
}
