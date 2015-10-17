package com.puppycrawl.tools.checkstyle.checks.design;

import com.google.common.collect.*;
//config.immutableClassName=com.google.google.common.ImmutableSet
public final class InputImmutableStarImport2
{
    public final ImmutableSet<String> set = null; // No warning here
}
