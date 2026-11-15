/*
TypeName
format = ^I_[a-zA-Z0-9]*$
tokens = INTERFACE_DEF
applyToPublic = (default)true
applyToProtected = (default)true
applyToPrivate = (default)true
applyToPackage = (default)true

*/

package org.checkstyle.suppressionxpathfilter.naming.typename;

public class InputXpathTypeNameInterfaceDef {

    public interface I_firstName {}
    interface SecondName {} // violation 'Name 'SecondName' must match pattern '^I_[a-zA-Z0-9]*$'.'

}
