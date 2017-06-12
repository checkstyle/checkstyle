package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck;
import com.puppycrawl.tools.checkstyle.checks.design.ThrowsCountCheck;
import com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheck;

import org.apache.commons.io.ByteOrderMark;

import static sun.tools.util.ModifierFilter.ALL_ACCESS;

import com.google.common.collect.HashMultimap; //warn, ORDER, should be on THIRD_PARTY_PACKAGE, now SPECIAL_IMPORTS

import antlr.*;
import antlr.CommonASTWithHiddenTokens;
import antlr.Token;
import antlr.collections.AST;

public class InputCustomImportOrderThirdPartyAndSpecial
{
}
/*
test: testThirdPartyAndSpecialImports()
configuration:
        checkConfig.addAttribute("specialImportsRegExp", "antlr.*");
        checkConfig.addAttribute("customImportOrderRules",
                "SAME_PACKAGE(3)###THIRD_PARTY_PACKAGE###STATIC###SPECIAL_IMPORTS");
*/
