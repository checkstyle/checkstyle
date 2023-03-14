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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableSet;

public final class InputVisibilityModifierImmutable
{
    public final int someIntValue;
    public final ImmutableSet<String> includes; // violation
    public final ImmutableSet<String> excludes; // violation
    public final java.lang.String notes;
    public final BigDecimal money;
    public final List list; // violation

    public InputVisibilityModifierImmutable(Collection<String> includes,Collection<String> excludes,
                                            BigDecimal value, String notes, int someValue, List l) {
        this.includes = ImmutableSet.copyOf(includes);
        this.excludes = ImmutableSet.copyOf(excludes);
        this.money = value;
        this.notes = notes;
        this.someIntValue = someValue;
        this.list = l;
    }

    final class Immutable
    {
        public final float f = 4;
        public final boolean bool = false;
        public final java.net.URI uri = null;
        public final java.io.File file = null;
        public int value = 42; // violation
        public final java.net.URL url = null;
        public boolean bValue = false; // violation
        public java.lang.Long longValue = 1L; // violation
    }

    class Example {
        final int C_D_E = 0; // violation
    }
}
