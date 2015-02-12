package com.puppycrawl.tools.checkstyle;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableSet;

public final class InputImmutable
{
    public final int someIntValue;
    public final ImmutableSet<String> includes;
    public final ImmutableSet<String> excludes;
    public final java.lang.String notes;
    public final BigDecimal value;
    public final List list;

    public InputImmutable(Collection<String> includes, Collection<String> excludes,
             BigDecimal value, String notes, int someValue, List l) {
        this.includes = ImmutableSet.copyOf(includes);
        this.excludes = ImmutableSet.copyOf(excludes);
        this.value = value;
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
        public int value = 42;
        public final java.net.URL url = null;
        public boolean bValue = false;
        public java.lang.Long longValue = 1L;
    }
}
