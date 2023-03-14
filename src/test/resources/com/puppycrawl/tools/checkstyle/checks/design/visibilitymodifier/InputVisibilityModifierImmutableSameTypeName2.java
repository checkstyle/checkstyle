/*
VisibilityModifier
packageAllowed = (default)false
protectedAllowed = (default)false
publicMemberPattern = (default)^serialVersionUID$
allowPublicFinalFields = (default)false
allowPublicImmutableFields = true
immutableClassCanonicalNames = (default)java.io.File, java.lang.Boolean, java.lang.Byte, \
                               java.lang.Character, java.lang.Double, java.lang.Float, \
                               java.lang.Integer, java.lang.Long, java.lang.Short, \
                               java.lang.StackTraceElement, java.lang.String, \
                               java.math.BigDecimal, java.math.BigInteger, \
                               java.net.Inet4Address, java.net.Inet6Address, \
                               java.net.InetSocketAddress, java.net.URI, java.net.URL, \
                               java.util.Locale, java.util.UUID
ignoreAnnotationCanonicalNames = (default)com.google.common.annotations.VisibleForTesting, \
                                 org.junit.ClassRule, org.junit.Rule


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier.inputs.InetSocketAddress;

public final class InputVisibilityModifierImmutableSameTypeName2
{
    public final java.util.GregorianCalendar calendar = null; // violation
    public final InputVisibilityModifierGregorianCalendar calendar2 = null; // violation
    public final InputVisibilityModifierGregorianCalendar calendar3 = null; // violation
    public final InetSocketAddress address = null; // violation
    public final java.net.InetSocketAddress adr = null;
}
