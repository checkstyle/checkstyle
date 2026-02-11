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
    public final java.util.GregorianCalendar calendar = null;
    // violation above 'Variable 'calendar' must be private and have accessor methods.'
    public final InputVisibilityModifierGregorianCalendar calendar2 = null;
    public final InputVisibilityModifierGregorianCalendar calendar3 = null;
    public final InetSocketAddress address = null;
    // violation above 'Variable 'address' must be private and have accessor methods.'
    public final InetSocketAddress adr = null;
    // violation above 'Variable 'adr' must be private and have accessor methods.'
}
