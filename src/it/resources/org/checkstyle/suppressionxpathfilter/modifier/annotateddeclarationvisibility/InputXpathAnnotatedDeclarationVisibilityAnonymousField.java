package org.checkstyle.suppressionxpathfilter.modifier.annotateddeclarationvisibility;

public class InputXpathAnnotatedDeclarationVisibilityAnonymousField {

    private final Object object = new Object() {
        @com.google.common.annotations.VisibleForTesting // warn
        int violationField;
    };

}
