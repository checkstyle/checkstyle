package com.puppycrawl.tools.checkstyle.checks.blocks;

public class InputDeclarationOrderAvoidDuplicatesInStaticFinalFields
{
    private boolean allowInSwitchCase;
    public static final String MSG_KEY_BLOCK_NESTED = "block.nested"; // static after final
                                                                      // public after private
}
