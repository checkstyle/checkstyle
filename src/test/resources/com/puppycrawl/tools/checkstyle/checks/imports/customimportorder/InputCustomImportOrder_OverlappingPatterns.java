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

// every import from javadoc package has comment in brackets indicating presence of keywords
// Javadoc, Check, Tag. For example J_T = Javadoc, no Check, Tag
// STANDARD - keyword Check
// SPECIAL_IMPORTS - keyword Tag

public class InputCustomImportOrder_OverlappingPatterns {
}
