/*
SuppressionXpathFilter
file = (file)InputSuppressionXpathFilter2.xml
optional = (default)false

com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck
id = uniqueId
format = (default)^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

public class InputSuppressionXpathFilter2 {
    public final static int different_name_than_suppression = 1; // violation
}
