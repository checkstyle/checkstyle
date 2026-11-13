/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfOrBranches {

    boolean requiredBeforeOr(Object value, boolean fallback) {
        return value != null && (value instanceof String || fallback);
    }

    boolean requiredBeforeReversedOr(Object value, boolean fallback) {
        return value != null && (fallback || value instanceof String);
    }

    boolean requiredAfterOr(Object value, boolean fallback) {
        return (value instanceof String || fallback) && value != null;
    }

    boolean requiredBeforeNestedOr(Object value, boolean first, boolean second) {
        return value != null && (first && (value instanceof String || second));
    }

    boolean requiredBeforeDereference(Object value) {
        return value != null && (value instanceof String || value.toString().isEmpty());
    }

    boolean redundantInLeftBranch(Object value, boolean fallback) {
        // violation below 'Unnecessary nullity check'
        return (value != null && value instanceof String) || fallback;
    }

    boolean redundantInRightBranch(Object value, boolean fallback) {
        // violation below 'Unnecessary nullity check'
        return fallback || (value != null && value instanceof String);
    }

    boolean redundantBeforeOr(Object value, boolean fallback) {
        // violation below 'Unnecessary nullity check'
        return value != null && value instanceof String
                && (fallback || value.toString().isEmpty());
    }

    boolean redundantWithUnconditionalInstanceOf(Object value, boolean fallback) {
        // violation below 'Unnecessary nullity check'
        return value != null && (value instanceof String || fallback)
                && value instanceof CharSequence;
    }

    boolean redundantAcrossOr(Object value) {
        // violation below 'Unnecessary nullity check'
        return value != null && (value instanceof String || value instanceof Integer);
    }

    boolean redundantAcrossNestedOr(Object value, boolean fallback) {
        // violation below 'Unnecessary nullity check'
        return value != null && ((value instanceof String && fallback)
                || ((fallback) && (value instanceof Integer)));
    }

    boolean requiredWithDifferentValues(Object value, Object other) {
        return value != null && (value instanceof String || other instanceof Integer);
    }

    boolean requiredWithMethodCall(Object value) {
        return value != null && (value instanceof String || value.toString() instanceof String);
    }

    boolean requiredBeforeDereferenceInRightBranch(Object value, boolean fallback) {
        return value != null && ((value instanceof String && fallback)
                || (value.toString().isEmpty() && value instanceof Integer));
    }

    boolean requiredBeforeDereferenceInLeftBranch(Object value, boolean fallback) {
        return value != null && ((value.toString().isEmpty() && value instanceof Integer)
                || (value instanceof String && fallback));
    }

    boolean redundantAcrossMultipleOrBranches(Object value) {
        // violation below 'Unnecessary nullity check'
        return value != null && (value instanceof String || value instanceof Integer
                || value instanceof Long);
    }

    boolean requiredWithConjunctionAlternative(Object value, boolean first, boolean second) {
        return value != null && (value instanceof String || (first && second));
    }

    boolean redundantInsideNonNullOr(Object value) {
        // violation below 'Unnecessary nullity check'
        return (value != null && value instanceof String) || value instanceof Integer;
    }

    boolean requiredBeforeDereferenceInConjunction(Object value) {
        return value != null && value.toString().isEmpty() && value instanceof String;
    }

    boolean redundantAfterDereferenceInConjunction(Object value) {
        return value instanceof String && value.toString().isEmpty()
                && value != null; // violation 'Unnecessary nullity check'
    }
}
