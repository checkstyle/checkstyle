/*
com.puppycrawl.tools.checkstyle.filters.SuppressionXpathFilter
file = (file)InputxpathAbstractRootNode.xml
optional = (default)false

com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck
format = [^a-zA-z0-9]*
ignoreCase = (default)false
message = (default)
tokens = CHAR_LITERAL

*/
package com.puppycrawl.tools.checkstyle.xpath.xpathabstractrootnode;

public class InputxpathAbstractRootNode {

    char a = '&'; // filtered violation
}
