package org.checkstyle.suppressionxpathfilter.design.innertypelast;

public class InputXpathInnerTypeLastFive {

    @interface MyAnnotation {
        String value() default "";
    }

    private int field; // warn

}
