/*
TypeName
format = ^I_[a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true
tokens = INTERFACE_DEF


*/
package org.checkstyle.suppressionxpathfilter.naming.typename;

public class InputXpathTypeNameInterfaceDef {

    public interface I_firstName {}
    interface SecondName {} // violation 'must match pattern'

}
