/*
SuppressionXpathFilter
file = src/SomeNonExistentFile.xml
optional = true

com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck
id = (null)
format = (default)^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

public class InputSuppressionXpathFilterNonExistentFileWithTrueOptional {

    public final static int bad_name = 1; // violation
}
