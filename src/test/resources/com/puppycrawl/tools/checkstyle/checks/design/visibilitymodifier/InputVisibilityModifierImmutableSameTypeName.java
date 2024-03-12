/*
VisibilityModifier
packageAllowed = (default)false
protectedAllowed = (default)false
publicMemberPattern = (default)^serialVersionUID$
allowPublicFinalFields = (default)false
allowPublicImmutableFields = true
immutableClassCanonicalNames = com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier.\
                               InputVisibilityModifierGregorianCalendar, com.puppycrawl.tools.\
                               checkstyle.checks.design.visibilitymodifier.inputs.InetSocketAddress
ignoreAnnotationCanonicalNames = (default)com.google.common.annotations.VisibleForTesting, \
                                 org.junit.ClassRule, org.junit.Rule


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import java.net.InetSocketAddress;

public final class InputVisibilityModifierImmutableSameTypeName
{
    public final java.util.GregorianCalendar calendar = null; // violation
    public final InputVisibilityModifierGregorianCalendar calendar2 = null;
    public final InputVisibilityModifierGregorianCalendar calendar3 = null;
    public final InetSocketAddress address = null; // violation
    public final InetSocketAddress adr = null; // violation
}
