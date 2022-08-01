/*
SuppressionXpathFilter
file = (file)InputSuppressionXpathFilterIdAndQuery.xml
optional = (default)false

com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck
id = uniqueId
format = (default)^[A-Z][A-Z\d]*(_[A-Z\d]+)*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

public class InputSuppressionXpathFilterReject {

    public final static int bad_name = 1; // filtered violation
}
