/*
TypeName
applyToPackage = (default)true
applyToPrivate = (default)true
applyToProtected = (default)true
applyToPublic = (default)true
format = ^I_[a-zA-Z0-9]*$
tokens = INTERFACE_DEF


*/

package org.checkstyle.suppressionxpathfilter.naming.typename;

public class InputXpathTypeNameInterfaceDef {

    public interface I_firstName {}
    // violation below "Name 'SecondName' must match pattern '^I_[a-zA-Z0-9]*$'."
    interface SecondName {}

}
