/*
VisibilityModifier
packageAllowed = (default)false
protectedAllowed = (default)false
publicMemberPattern = (default)^serialVersionUID$
allowPublicFinalFields = (default)false
allowPublicImmutableFields = true
immutableClassCanonicalNames = PackageClass
ignoreAnnotationCanonicalNames = (default)com.google.common.annotations.VisibleForTesting, \
                                 org.junit.ClassRule, org.junit.Rule


*/

//non-compiled: bad import for testing
package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import com.PackageClass.*;

public final class InputVisibilityModifierPackageClassName {
    public final PackageClass o = new PackageClass();
}
