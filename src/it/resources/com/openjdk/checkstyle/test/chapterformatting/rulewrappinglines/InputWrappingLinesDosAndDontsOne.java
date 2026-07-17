package com.openjdk.checkstyle.test.chapterformatting.rulewrappinglines;

// violation first line 'Header mismatch*'

import java.util.stream.Stream;
import static java.util.stream.Collectors.joining;

public class InputWrappingLinesDosAndDontsOne {

    class Errors {
        static Error invalidRepeatableAnnotationNotApplicable(boolean targetContainerType,
                boolean on) {
            return new Error("Error");
        }

        static Error invalidRepeatableAnnotationNotApplicableInContext(
                boolean targetContainerType) {
            return new Error("Error");
        }

    }

    class Argument {
        static String prettyPrint(String str) {
            return "";
        }
    }

    public void styleGuideDosLineLimit(boolean isTypeParam, String args) {
        boolean targetContainerType = false;
        boolean on = false;
        Error e = isTypeParam
                ? Errors.invalidRepeatableAnnotationNotApplicable(targetContainerType, on)
                : Errors.invalidRepeatableAnnotationNotApplicableInContext(targetContainerType);

        String pretty = Stream.of(args)
                .map(Argument::prettyPrint)
                .collect(joining(", "));
    }

    public void styleGuideDontsLineLimit(boolean isTypeParam, String args) {
        boolean targetContainerType = false;
        boolean on = false;

        // checkstyle is a static tool it can not determine whether line wrapping increase
        // readability or not
        Error e = isTypeParam
                ? Errors.invalidRepeatableAnnotationNotApplicable(
                        targetContainerType, on)
                : Errors.invalidRepeatableAnnotationNotApplicableInContext(
                        targetContainerType);

        // Not covered until https://github.com/checkstyle/checkstyle/issues/20714
        String pretty = Stream.of(args).map(Argument::prettyPrint).collect(joining(", "));
    }

    public String withMany(int arg, int that, int needs) {
        return "";
    }

    public void methodCall(String first, int second) {
    }

    public int to(int first, int second) {
        return 0;
    }

    public void styleGuideDosSyntax(int arguments, int that, int needs, int be, int wrapped,
            int tos, int avoid, int veryLong, int lines) {

        methodCall(withMany(arguments, that, needs),
                to(be, (wrapped - tos) * avoid / veryLong - lines));
    }

    public void styleGuideDontsSyntax(int arguments, int that, int needs, int be, int wrapped,
            int tos, int avoid, int veryLong, int lines) {

        // Not covered until https://github.com/checkstyle/checkstyle/issues/20716
        methodCall(withMany(arguments, that, needs), to(be, (wrapped
                - tos) * avoid / veryLong - lines));
    }

    public String styleGuideDosStatement() {
        int i = 1;
        int j = 2;
        int k = 3;
        boolean condition = false;
        String expression = "";

        i += j;
        j += k;
        if (condition) {
            return expression;
        }
        return "";
    }

    public String styleGuideDontsStatement() {
        int i = 1;
        int j = 2;
        int k = 3;
        boolean condition = false;
        String expression = "";

        i += j; j += k; // violation 'Only one statement'

        // violation below ''{' at column 24 should have line break after.'
        if (condition) { return expression; }
        return "";
    }

}
