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
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocNodeImpl; 
// violation 'Wrong order for 'com.*.JavadocNodeImpl' import.' 

// STANDARD - keyword Check

import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck; 
// violation 'Wrong order for 'com.*.javadoc.AbstractJavadocCheck' import.'
import com.puppycrawl.tools.checkstyle.checks.javadoc.AtclauseOrderCheck; // (_C_)
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagContinuationIndentationCheck; // (JCT)

// SPECIAL_IMPORTS - keyword Tag

import com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocTag; 
// violation 'Wrong order for 'com.*.javadoc.InvalidJavadocTag'
//import com.puppycrawl.tools.checkstyle.checks.javadoc.TagParser; // (__T)
import com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck; 
// violation 'Wrong order for 'com.*.javadoc.WriteTagCheck' import.'

import com.puppycrawl.tools.checkstyle.*;
//import com.puppycrawl.tools.checkstyle.checks.javadoc.HtmlTag;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTag; 
// violation 'Wrong order for 'com.*.javadoc.JavadocTag' import.' 
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck; 
// violation 'Wrong order for 'com.*.javadoc.JavadocMethodCheck' import.' 
import com.puppycrawl.tools.checkstyle.checks.javadoc.NonEmptyAtclauseDescriptionCheck; 
// violation 'Wrong order for 'com.*.NonEmptyAtclauseDescriptionCheck' import.'

public class InputCustomImportOrder_OverlappingPatterns {
}
