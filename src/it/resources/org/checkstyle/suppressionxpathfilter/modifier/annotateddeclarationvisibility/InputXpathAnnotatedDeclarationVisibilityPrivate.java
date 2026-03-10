package org.checkstyle.suppressionxpathfilter.modifier.annotateddeclarationvisibility;

public class InputXpathAnnotatedDeclarationVisibilityPrivate {

    @com.google.common.annotations.VisibleForTesting
    private void violationMethod() {} // warn

}
