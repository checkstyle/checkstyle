package com.puppycrawl.tools.checkstyle.checks.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public class InputAnnotationsUseStyleParams
{
    @Target({})
    public @interface myAnn {
        
    }
}
