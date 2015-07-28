package com.puppycrawl.tools.checkstyle.imports;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;

import org.apache.commons.io.ByteOrderMark;

import static sun.tools.util.ModifierFilter.ALL_ACCESS;

import com.google.common.annotations.GwtCompatible; //warn, ORDER, should be on THIRD_PARTY_PACKAGE, now SPECIAL_IMPORTS

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
