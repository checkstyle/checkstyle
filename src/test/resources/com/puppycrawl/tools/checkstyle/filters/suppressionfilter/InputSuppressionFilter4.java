/*
SuppressionFilter
file = (file)InputSuppressionFilter4.xml
optional = (default)false

com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck
format = (default)^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.filters.suppressionfilter;

public class InputSuppressionFilter4 {
    public final static int different_name_than_suppression = 1;
    // filtered violation above ''different_name_than_suppression' must match pattern'
}
