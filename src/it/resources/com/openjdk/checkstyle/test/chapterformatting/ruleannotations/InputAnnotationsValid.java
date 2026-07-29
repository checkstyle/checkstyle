package com.openjdk.checkstyle.test.chapterformatting.ruleannotations;

// violation first line 'Header mismatch'

@Deprecated
@SuppressWarnings("unused")
public class InputAnnotationsValid {

    @Deprecated @SuppressWarnings("unused")
    public InputAnnotationsValid() {
    }

    @Deprecated
    @SuppressWarnings("unused")
    public void annotationsOnSeparateLines() {
    }

    @Deprecated @SuppressWarnings("unused")
    public void annotationsOnSameLine() {
    }

    @Deprecated public void annotationOnSingleLineMethod() { }
}
