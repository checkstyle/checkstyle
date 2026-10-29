/*
TypeName
applyToPackage = (default)true
applyToPrivate = (default)true
applyToProtected = (default)true
applyToPublic = (default)true
format = (default)^[A-Z][a-zA-Z0-9]*$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package org.checkstyle.suppressionxpathfilter.naming.typename;

public class InputXpathTypeNameDefault {
    public interface FirstName {}
    // violation below "Name 'SecondName_' must match pattern '^[A-Z][a-zA-Z0-9]*$'."
    private class SecondName_ {}

}
