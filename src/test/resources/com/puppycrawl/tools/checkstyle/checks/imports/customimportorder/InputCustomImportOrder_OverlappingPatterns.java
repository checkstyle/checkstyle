/*
CustomImportOrder
customImportOrderRules = THIRD_PARTY_PACKAGE###SAME_PACKAGE(6)###STANDARD_JAVA_PACKAGE###\
                         SPECIAL_IMPORTS
standardPackageRegExp = com.puppycrawl.tools.*Check$
thirdPartyPackageRegExp = com.puppycrawl.tools.checkstyle.checks.javadoc.*Javadoc*
specialImportsRegExp = com.puppycrawl.tools.*Tag*
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/

//non-compiled with javac: special package and requires imports from the same package
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderOption;

// every import from javadoc package has comment in brackets indicating presence of keywords
// Javadoc, Check, Tag. For example J_T = Javadoc, no Check, Tag
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocNodeImpl; // violation '.* wrong order..* expecting group .* on this line'

// STANDARD - keyword Check

import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck; // violation '.* should be separated from previous import group by one line'
import com.puppycrawl.tools.checkstyle.checks.javadoc.AtclauseOrderCheck; // (_C_)
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagContinuationIndentationCheck; // (JCT)

// SPECIAL_IMPORTS - keyword Tag

import com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocTag; // violation '.* should be separated from previous import group by one line'
//import com.puppycrawl.tools.checkstyle.checks.javadoc.TagParser; // (__T)
import com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck; // violation '.* is in the wrong order. Should be in the .*group, expecting not assigned imports.*'

import com.puppycrawl.tools.checkstyle.*;
//import com.puppycrawl.tools.checkstyle.checks.javadoc.HtmlTag;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTag; // violation '.* wrong order. Should be in the .*group, expecting not assigned imports.*'
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck; // violation '.* wrong order. Should be in the .*group, expecting not assigned imports.*'
import com.puppycrawl.tools.checkstyle.checks.javadoc.NonEmptyAtclauseDescriptionCheck; // violation '.* wrong order. Should be in the .*group, expecting not assigned imports.*'

public class InputCustomImportOrder_OverlappingPatterns {
}
