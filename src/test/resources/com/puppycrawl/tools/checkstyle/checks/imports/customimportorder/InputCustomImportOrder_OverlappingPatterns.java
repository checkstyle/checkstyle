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

import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderOption;

// every import from javadoc package has comment in brackets indicating presence of keywords
// Javadoc, Check, Tag. For example J_T = Javadoc, no Check, Tag
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocNodeImpl; // violation 'Import statement for 'com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocNodeImpl' is in the wrong order. Should be in the 'THIRD_PARTY_PACKAGE' group, expecting group 'STANDARD_JAVA_PACKAGE' on this line'

// STANDARD - keyword Check

import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck; // violation ''com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck'' should be separated from previous import group by one line'
import com.puppycrawl.tools.checkstyle.checks.javadoc.AtclauseOrderCheck; // (_C_)
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagContinuationIndentationCheck; // (JCT)

// SPECIAL_IMPORTS - keyword Tag

import com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocTag; // violation ''com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocTag' should be separated from previous import group by one line'
//import com.puppycrawl.tools.checkstyle.checks.javadoc.TagParser; // (__T)
import com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck; // violation 'Import statement for 'com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck' is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group, expecting not assigned imports on this line'

import com.puppycrawl.tools.checkstyle.*;
//import com.puppycrawl.tools.checkstyle.checks.javadoc.HtmlTag;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTag; // violation 'Import statement for 'com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTag' is in the wrong order. Should be in the 'SPECIAL_IMPORTS' group, expecting not assigned imports on this line'
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck; // violation 'Import statement for 'com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck' is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group, expecting not assigned imports on this line'
import com.puppycrawl.tools.checkstyle.checks.javadoc.NonEmptyAtclauseDescriptionCheck; // violation 'Import statement for 'com.puppycrawl.tools.checkstyle.checks.javadoc.NonEmptyAtclauseDescriptionCheck' is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group, expecting not assigned imports on this line'

public class InputCustomImportOrder_OverlappingPatterns {
}
