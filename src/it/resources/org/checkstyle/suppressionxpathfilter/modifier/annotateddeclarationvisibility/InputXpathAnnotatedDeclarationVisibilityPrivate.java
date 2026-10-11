package org.checkstyle.suppressionxpathfilter.modifier.annotateddeclarationvisibility;

public class InputXpathAnnotatedDeclarationVisibilityPrivate {

    static class Inner {
        @com.google.common.annotations.VisibleForTesting // warn
        private void violationMethod() {}
    }

}
