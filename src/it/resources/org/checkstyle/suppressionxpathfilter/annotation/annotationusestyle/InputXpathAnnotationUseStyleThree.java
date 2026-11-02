/*
AnnotationUseStyle
closingParens = (default)never
elementStyle = (default)compact_no_array
trailingArrayComma = ignore


*/

package org.checkstyle.suppressionxpathfilter.annotation.annotationusestyle;

public class InputXpathAnnotationUseStyleThree {
    // violation below 'Annotation style must be 'COMPACT_NO_ARRAY'.'
    @SuppressWarnings({"common",})
    public void foo() {

    }
}
