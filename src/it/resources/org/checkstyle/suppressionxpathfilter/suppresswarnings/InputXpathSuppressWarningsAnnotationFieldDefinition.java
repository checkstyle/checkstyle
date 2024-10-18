package org.checkstyle.suppressionxpathfilter.suppresswarnings;

public @interface InputXpathSuppressWarningsAnnotationFieldDefinition {
        @SuppressWarnings("") // warn
        String foo() default "bar";
}
