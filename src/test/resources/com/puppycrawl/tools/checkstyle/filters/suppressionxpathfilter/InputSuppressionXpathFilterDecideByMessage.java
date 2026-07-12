/*
SuppressionXpathFilter
file = (file)InputSuppressionXpathFilterDecideByMessage.xml
optional = (default)false

com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck
id = (null)
format = (default)^[A-Z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true
tokens = CLASS_DEF


*/
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

// The lowercase first letter intentionally triggers a TypeNameCheck violation
public class inputSuppressionXpathFilterDecideByMessage { // violation
}
