/*
TypeName
format = (default)^[A-Z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/
package org.checkstyle.suppressionxpathfilter.naming.typename;

public class InputXpathTypeNameDefault {
    public interface FirstName {}
    private class SecondName_ {} // violation 'must match pattern'

}
