package org.checkstyle.suppressionxpathfilter.modifier.annotateddeclarationvisibility;

public class InputXpathAnnotatedDeclarationVisibilityPackagePrivate {

    private final Object object = new Object() {
        @com.google.common.annotations.VisibleForTesting // warn
        void violationMethod() {}
    };

}
