/*
Indentation
arrayInitIndent = (default)4
basicOffset = (default)4
braceAdjustment = (default)0
caseIndent = (default)4
forceStrictCondition = true
lineWrappingIndentation = 8
throwsIndent = (default)4

*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

public class InputIndentationNestedCallArgumentsOffset {
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
        new InputIndentationNestedCallArgumentsOffset(
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

    InputIndentationNestedCallArgumentsOffset(String... values) { }

    InputIndentationNestedCallArgumentsOffset(int ignored) {
        this(
                String.valueOf(
                        "value"
                )
        );
    }
}
