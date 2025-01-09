/*
AnnotationUseStyle
elementStyle = (default)COMPACT_NO_ARRAY
closingParens = (default)NEVER
trailingArrayComma = (default)NEVER


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public class InputAnnotationUseStyleParams
{
    @Target({})
    public @interface myAnn {

    }
}
