package com.puppycrawl.tools.checkstyle.checks.design;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableSet;

public class InputPublicImmutable {    
    public final int someIntValue;
    public final ImmutableSet<String> includes;
    public final java.lang.String notes;
    public final BigDecimal value;
    public final List list;
    public InputPublicImmutable(Collection<String> includes,
            BigDecimal value, String notes, int someValue, List l) {
        this.includes = ImmutableSet.copyOf(includes);
        this.value = value;
        this.notes = notes;
        this.someIntValue = someValue;
        this.list = l;
    }
}
