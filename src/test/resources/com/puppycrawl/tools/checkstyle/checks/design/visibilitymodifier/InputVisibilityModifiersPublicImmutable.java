/*
VisibilityModifier
packageAllowed = (default)false
protectedAllowed = (default)false
publicMemberPattern = (default)^serialVersionUID$
allowPublicFinalFields = (default)false
allowPublicImmutableFields = (default)false
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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableSet;

public class InputVisibilityModifiersPublicImmutable {
    public final int someIntValue;
    // violation above 'Variable 'someIntValue' must be private and have accessor methods.'
    public final ImmutableSet<String> includes;
    // violation above 'Variable 'includes' must be private and have accessor methods.'
    public final java.lang.String notes;
    // violation above 'Variable 'notes' must be private and have accessor methods.'
    public final BigDecimal value;
    // violation above 'Variable 'value' must be private and have accessor methods.'
    public final List list;
    // violation above 'Variable 'list' must be private and have accessor methods.'
    public InputVisibilityModifiersPublicImmutable(Collection<String> includes,
                                            BigDecimal value, String notes, int someValue, List l) {
        this.includes = ImmutableSet.copyOf(includes);
        this.value = value;
        this.notes = notes;
        this.someIntValue = someValue;
        this.list = l;
    }
}
