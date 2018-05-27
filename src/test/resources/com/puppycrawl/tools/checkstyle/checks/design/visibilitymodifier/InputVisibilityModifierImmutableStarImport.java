package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier.InputVisibilityModifierImmutable;
import com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier.inputs.InetSocketAddress.*;

public final class InputVisibilityModifierImmutableStarImport
{
    public final Arrays f = null; // If Arrays is specified as immutable class,
                                  // no matter of canonical name
                                  // no warning will be here, star imports are out of consideration
}
