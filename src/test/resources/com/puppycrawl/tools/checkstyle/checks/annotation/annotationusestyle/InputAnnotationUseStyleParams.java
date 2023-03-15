/*
AnnotationUseStyle
elementStyle = (default)compact_no_array
closingParens = (default)never
trailingArrayComma = (default)never


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public class InputAnnotationUseStyleParams
{
    @Target({}) // ok
    public @interface myAnn {

    }
}
