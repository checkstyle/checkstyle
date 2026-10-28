/*
ConstantName
applyToPackage = (default)true
applyToPrivate = (default)true
applyToProtected = (default)true
applyToPublic = (default)true
format = (default)^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$


*/

package org.checkstyle.suppressionxpathfilter.naming.constantname;

public class InputXpathConstantNameWithBeginningUnderscore {
    private static final String _CONSTANT = "a"; // violation 'must match pattern'
}
