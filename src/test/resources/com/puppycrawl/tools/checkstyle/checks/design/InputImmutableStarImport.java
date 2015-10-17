package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.checks.design.InputImmutable;
import com.puppycrawl.tools.checkstyle.checks.design.InetSocketAddress.*;

public final class InputImmutableStarImport
{
    public final Arrays f = null; // If Arrays is specified as immutable class, no matter of canonical name
                                  // no warning will be here, star imports are out of consideration
}
