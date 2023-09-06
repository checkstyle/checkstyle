/*
VisibilityModifier
packageAllowed = (default)false
protectedAllowed = (default)false
publicMemberPattern = (default)^serialVersionUID$
allowPublicFinalFields = (default)false
allowPublicImmutableFields = true
immutableClassCanonicalNames = java.lang.String, com.google.common.collect.ImmutableSet
ignoreAnnotationCanonicalNames = (default)com.google.common.annotations.VisibleForTesting, \
                                 org.junit.ClassRule, org.junit.Rule


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import com.google.common.collect.*;
//config.immutableClassName=com.google.google.common.ImmutableSet
public final class InputVisibilityModifierImmutableStarImport2
{
    public final ImmutableSet<String> set = null; // ok
}
