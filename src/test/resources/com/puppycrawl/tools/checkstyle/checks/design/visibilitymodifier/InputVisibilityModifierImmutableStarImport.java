/*
VisibilityModifier
packageAllowed = (default)false
protectedAllowed = (default)false
publicMemberPattern = (default)^serialVersionUID$
allowPublicFinalFields = (default)false
allowPublicImmutableFields = true
immutableClassCanonicalNames = java.util.Arrays
ignoreAnnotationCanonicalNames = (default)com.google.common.annotations.VisibleForTesting, \
                                 org.junit.ClassRule, org.junit.Rule


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier.InputVisibilityModifierImmutable;
import com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier.inputs.InetSocketAddress.*;

public final class InputVisibilityModifierImmutableStarImport // ok
{
    public final Arrays f = null; // If Arrays is specified as immutable class,
                                  // no matter of canonical name
                                  // no warning will be here, star imports are out of consideration
}
