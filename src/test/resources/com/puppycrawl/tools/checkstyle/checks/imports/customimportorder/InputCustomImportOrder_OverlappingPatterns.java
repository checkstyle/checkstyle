package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderOption;

// every import from javadoc package has comment in brackets indicating presence of keywords
// Javadoc, Check, Tag. For example J_T = Javadoc, no Check, Tag
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocNodeImpl; //warn, should be on THIRD-PARTY (J__)

// STANDARD - keyword Check

import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck; // (JC_)
import com.puppycrawl.tools.checkstyle.checks.javadoc.AtclauseOrderCheck; // (_C_)
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagContinuationIndentationCheck; // (JCT)

// SPECIAL_IMPORTS - keyword Tag

import com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocTag; // (J_T)
//import com.puppycrawl.tools.checkstyle.checks.javadoc.TagParser; // (__T)
import com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck; //warn, should be on STANDARD (_CT)

import com.puppycrawl.tools.*;
//import com.puppycrawl.tools.checkstyle.checks.javadoc.HtmlTag; //warn, should be on SPECIAL (__T)
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTag; //warn, should be on SPECIAL (J_T)
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck; //warn, should be on STANDARD  (JC_)
import com.puppycrawl.tools.checkstyle.checks.javadoc.NonEmptyAtclauseDescriptionCheck; //warn, should be on STANDARD (_C_)

public class InputCustomImportOrder_OverlappingPatterns {
}
/*
test: testRulesOrder_ThirdBeforeSame()
configuration:
        checkConfig.addAttribute("customImportOrderRules",
                "THIRD_PARTY_PACKAGE###SAME_PACKAGE(3)###SPECIAL_IMPORTS");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
*/
