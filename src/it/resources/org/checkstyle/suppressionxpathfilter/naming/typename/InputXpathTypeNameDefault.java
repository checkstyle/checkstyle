/*
TypeName
format = (default)^[A-Z][a-zA-Z0-9]*$
tokens = (default)CLASS_DEF,INTERFACE_DEF,ENUM_DEF,ANNOTATION_DEF,RECORD_DEF
applyToPublic = (default)true
applyToProtected = (default)true
applyToPrivate = (default)true
applyToPackage = (default)true

*/

package org.checkstyle.suppressionxpathfilter.naming.typename;

public class InputXpathTypeNameDefault {
    public interface FirstName {}
    private class SecondName_ {} // violation 'Name 'SecondName_' must match pattern '^[A-Z][a-zA-Z0-9]*$'.'

}
