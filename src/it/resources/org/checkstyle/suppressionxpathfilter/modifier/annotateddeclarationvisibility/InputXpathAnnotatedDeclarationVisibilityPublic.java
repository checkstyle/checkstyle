package org.checkstyle.suppressionxpathfilter.modifier.annotateddeclarationvisibility;

public class InputXpathAnnotatedDeclarationVisibilityPublic {

    @com.google.common.annotations.VisibleForTesting
    public void violationMethod() {} // warn

}
