package org.checkstyle.suppressionxpathfilter.modifier.annotateddeclarationvisibility;

public class InputXpathAnnotatedDeclarationVisibilityPrivate {

    @com.google.common.annotations.VisibleForTesting // warn
    private void violationMethod() {}

}
