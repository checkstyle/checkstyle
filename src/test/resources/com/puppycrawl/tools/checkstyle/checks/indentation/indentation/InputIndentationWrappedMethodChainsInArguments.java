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

import java.util.List;

class InputIndentationWrappedMethodChainsInArguments {
    void nestedChains(List<String> values, StringBuilder outer, StringBuilder inner) {
        values.add(
            2,
            outer.append("a")
                .append("b")
                .append(inner
                    .append("x")
                    .append("y")
                    .toString())
                .toString());
    }

    void chainAtArgumentIndent(List<String> values, StringBuilder builder) {
        values.add(
            builder.append("a")
            .toString());
    }

    void misalignedChains(List<String> values, StringBuilder outer, StringBuilder inner) {
        values.add(
            outer.append("a")
              .toString()); // violation ''.' has incorrect indentation level 14'
        values.add(
            outer.append("a")
                .append(inner
                        .append("b")) // violation ''.' has incorrect indentation level 24'
                .toString());
    }

    void chainInForInitializer(StringBuilder builder, boolean keepGoing) {
        for (
            builder.append("a")
                .append("b"); // violation ''.' has incorrect indentation level 16'
            keepGoing;
            builder.append("c")) {
            break;
        }
    }
}
