package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;


/* Config:
 * closingParens = ignore
 * elementStyle = COMPACT_NO_ARRAY
 * trailingArrayComma = ignore
 */
@interface InputAnnotationUseStyle {
    @Another32(value={"foo", "bar"}) //expanded // ok
    DOGS[] pooches();
}


@interface Another32 {
    String[] value();
}
