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

import com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier.InputVisibilityModifierGregorianCalendar;
import com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier.inputs.InetSocketAddress;
public final class InputVisibilityModifierImmutableSameTypeName
{
    public final java.util.GregorianCalendar calendar = null; // violation
    public final InputVisibilityModifierGregorianCalendar calendar2 = null;
    public final com.puppycrawl.tools.checkstyle.checks.design.
            visibilitymodifier.InputVisibilityModifierGregorianCalendar calendar3 = null;
    public final InetSocketAddress address = null;
    public final java.net.InetSocketAddress adr = null; // violation
}
