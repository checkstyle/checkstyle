/*
InnerTypeLast


*/

package org.checkstyle.suppressionxpathfilter.design.innertypelast;

public class InputXpathInnerTypeLastFive {

    @interface MyAnnotation {
        String value() default "";
    }

    // violation 2 lines below """Init blocks, constructors, fields and methods
    //     should be before inner types."""
    private int field;

}

