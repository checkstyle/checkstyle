package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/* Config:
 * closingParens = NEVER
 */
public class InputAnnotationUseStyleParams
{
    @Target({}) // ok
    public @interface myAnn {

    }
}
