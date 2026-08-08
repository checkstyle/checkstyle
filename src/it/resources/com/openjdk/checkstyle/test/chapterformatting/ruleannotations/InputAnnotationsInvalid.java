package com.openjdk.checkstyle.test.chapterformatting.ruleannotations;

// violation first line 'Header mismatch'

// violation below 'Annotations must be on a separate line from 'InputAnnotationsInvalid'.'
@Deprecated public class InputAnnotationsInvalid {

    // violation below 'Annotations must be on a separate line from 'InputAnnotationsInvalid'.'
    @Deprecated InputAnnotationsInvalid() {
    }

    // violation below 'Annotations must be on a separate line from 'annotationOnMultilineMethod'.'
    @Deprecated public void annotationOnMultilineMethod() {
    }

    // violation 4 lines below """Annotations on 'annotationsOnMixedLines' must be all on one
    // line or all on separate lines."""
    @Deprecated @SuppressWarnings("unused")
    @SafeVarargs
    public final void annotationsOnMixedLines(String... arguments) {
    }

    @Deprecated
    public void annotationOnSeparateLine() {
    }
}
