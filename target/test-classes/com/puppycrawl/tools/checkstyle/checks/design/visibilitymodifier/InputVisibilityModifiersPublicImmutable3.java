/*
VisibilityModifier
packageAllowed = (default)false
protectedAllowed = (default)false
publicMemberPattern = (default)^serialVersionUID$
allowPublicFinalFields = true
allowPublicImmutableFields = (default)false
immutableClassCanonicalNames = com.google.common.collect.ImmutableSet
ignoreAnnotationCanonicalNames = (default)com.google.common.annotations.VisibleForTesting, \
                                 org.junit.ClassRule, org.junit.Rule


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class InputVisibilityModifiersPublicImmutable3 {
    public final int someIntValue;
    public final ImmutableSet<String> includes;
    public final String notes;
    public final BigDecimal value;
    public final List list;
    public InputVisibilityModifiersPublicImmutable3(Collection<String> includes,
           BigDecimal value, String notes, int someValue, List l) {
        this.includes = ImmutableSet.copyOf(includes);
        this.value = value;
        this.notes = notes;
        this.someIntValue = someValue;
        this.list = l;
    }
}
