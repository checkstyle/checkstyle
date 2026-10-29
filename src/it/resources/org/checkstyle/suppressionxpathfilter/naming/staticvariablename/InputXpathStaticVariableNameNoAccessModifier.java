/*
StaticVariableName
applyToPackage = (default)true
applyToPrivate = (default)true
applyToProtected = (default)true
applyToPublic = (default)true
format = (default)^[a-z][a-zA-Z0-9]*$


*/

package org.checkstyle.suppressionxpathfilter.naming.staticvariablename;

public class InputXpathStaticVariableNameNoAccessModifier {
    static int NUM3; // violation 'Name 'NUM3' must match pattern '^[a-z][a-zA-Z0-9]*$'.'
    {
    }
}
