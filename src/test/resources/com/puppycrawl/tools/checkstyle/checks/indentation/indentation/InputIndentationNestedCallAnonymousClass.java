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

public class InputIndentationNestedCallAnonymousClass {
    void valid() {
        consume(
            new Object() {
                void method()
                    throws Exception {
                    consume(
                        consume(
                            "value"
                        )
                    );
                }
            });
    }

    void invalid() {
        consume(
            new Object() {
                void method()
                  throws Exception { // violation 'incorrect indentation level 18.*20'
                }
            });
    }

    Object consume(Object value) {
        return value;
    }
}
