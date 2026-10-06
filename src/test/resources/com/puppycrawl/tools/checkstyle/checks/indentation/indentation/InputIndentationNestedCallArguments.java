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

public class InputIndentationNestedCallArguments {
    void valid() {
        call(
            call(
                "value"
            )
        );
        call(
            call(
                call(
                    "value"
                )
            ),
            "other"
        );
        call(
            this.call(
                "value"
            ),
            "other"
        );
        call(call(
            "value"
        ));
        call(call
            (
            "value"
        ));
        call(
            call("value")
            .substring(
                0
            )
        );
        String result = call(
            call(
                "value"
            )
        );
        new InputIndentationNestedCallArguments(
            call(
                "value"
            )
        );
    }

    String returned() {
        return call(
            call(
                "value"
            )
        );
    }

    private String call(String... values) {
        return null;
    }

    InputIndentationNestedCallArguments(String... values) { }

    InputIndentationNestedCallArguments(int ignored) {
        this(
            String.valueOf(
                "value"
            )
        );
    }
}
