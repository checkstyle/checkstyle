package com.puppycrawl.tools.checkstyle.imports;

import javax.xml.transform.Source;

import org.w3c.dom.Node;

class DOMSource {}

/*
test: testPossibleIndexOutOfBoundsException()
configuration:
        checkConfig.addAttribute("thirdPartyPackageRegExp", ".*");
        checkConfig.addAttribute("specialImportsRegExp", "com.google");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        checkConfig.addAttribute("customImportOrderRules",
                "STATIC###SPECIAL_IMPORTS###THIRD_PARTY_PACKAGE###STANDARD_JAVA_PACKAGE");
*/
