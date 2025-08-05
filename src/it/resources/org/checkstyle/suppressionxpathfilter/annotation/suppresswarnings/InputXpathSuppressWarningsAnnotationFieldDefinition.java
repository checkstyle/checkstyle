package org.checkstyle.suppressionxpathfilter.annotation.suppresswarnings;

public @interface InputXpathSuppressWarningsAnnotationFieldDefinition {
        @SuppressWarnings("") // warn
        String foo() default "bar";
}
