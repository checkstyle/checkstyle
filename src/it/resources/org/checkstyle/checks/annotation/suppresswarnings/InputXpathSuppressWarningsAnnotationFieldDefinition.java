package org.checkstyle.checks.annotation.suppresswarnings;

public @interface InputXpathSuppressWarningsAnnotationFieldDefinition {
        @SuppressWarnings("") // warn
        String foo() default "bar";
}
