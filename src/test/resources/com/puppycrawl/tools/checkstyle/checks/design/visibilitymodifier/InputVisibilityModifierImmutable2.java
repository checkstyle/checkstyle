/*
VisibilityModifier
packageAllowed = (default)false
protectedAllowed = (default)false
publicMemberPattern = (default)^serialVersionUID$
allowPublicFinalFields = (default)false
allowPublicImmutableFields = true
immutableClassCanonicalNames = String, Integer, Byte, Character, Short, Boolean, Long, Double, \
                               Float, StackTraceElement, BigInteger, BigDecimal, File, Locale, \
                               UUID, URL, URI, Inet4Address, Inet6Address, InetSocketAddress
ignoreAnnotationCanonicalNames = (default)com.google.common.annotations.VisibleForTesting, \
                                 org.junit.ClassRule, org.junit.Rule


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public final class InputVisibilityModifierImmutable2
{
    public final int someIntValue;
    public final ImmutableSet<String> includes; // violation
    public final ImmutableSet<String> excludes; // violation
    public final String notes;
    public final BigDecimal money; // violation
    public final List list; // violation

    public InputVisibilityModifierImmutable2(Collection<String> includes,
           Collection<String> excludes, BigDecimal value, String notes, int someValue, List l) {
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
        public final java.net.URI uri = null; // violation
        public final java.io.File file = null; // violation
        public int value = 42; // violation
        public final java.net.URL url = null; // violation
        public boolean bValue = false; // violation
        public Long longValue = 1L; // violation
    }

    class Example {
        final int C_D_E = 0; // violation
    }
}
