/*
VisibilityModifier
packageAllowed = (default)false
protectedAllowed = (default)false
publicMemberPattern = (default)^serialVersionUID$
allowPublicFinalFields = (default)false
allowPublicImmutableFields = true
immutableClassCanonicalNames = java.util.List, com.google.common.collect.ImmutableSet, \
                               java.lang.String
ignoreAnnotationCanonicalNames = (default)com.google.common.annotations.VisibleForTesting, \
                                 org.junit.ClassRule, org.junit.Rule


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public final class InputVisibilityModifierImmutable5
{
    public final int someIntValue;
    public final ImmutableSet<String> includes;
    public final ImmutableSet<String> excludes;
    public final String notes;
    public final BigDecimal money;
    // violation above 'Variable 'money' must be private and have accessor methods.'
    public final List list;

    public InputVisibilityModifierImmutable5(Collection<String> includes,
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
        public final java.net.URI uri = null;
        // violation above 'Variable 'uri' must be private and have accessor methods.'
        public final java.io.File file = null;
        // violation above 'Variable 'file' must be private and have accessor methods.'
        public int value = 42;
        // violation above 'Variable 'value' must be private and have accessor methods.'
        public final java.net.URL url = null;
        // violation above 'Variable 'url' must be private and have accessor methods.'
        public boolean rate = false;
        // violation above 'Variable 'rate' must be private and have accessor methods.'
        public Long id = 1L;
        // violation above 'Variable 'id' must be private and have accessor methods.'
    }

    class Example {
        final int C_D_E = 0;
        // violation above 'Variable 'C_D_E' must be private and have accessor methods.'
    }
}
