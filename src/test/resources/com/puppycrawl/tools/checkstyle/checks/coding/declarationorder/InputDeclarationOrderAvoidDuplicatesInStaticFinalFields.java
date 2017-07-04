package com.puppycrawl.tools.checkstyle.checks.coding.declarationorder;

public class InputDeclarationOrderAvoidDuplicatesInStaticFinalFields
{
    private boolean allowInSwitchCase;
    public static final String MSG_KEY_BLOCK_NESTED = "block.nested"; // static after final
                                                                      // public after private
}
