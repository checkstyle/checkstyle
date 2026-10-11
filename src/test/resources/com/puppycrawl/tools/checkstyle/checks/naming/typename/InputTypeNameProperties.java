/*
TypeName
format = (default)^[A-Z][a-zA-Z0-9]*$
applyToPublic = false
applyToProtected = false
applyToPackage = false
applyToPrivate = false
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.typename;

public class InputTypeNameProperties {
    class someType {}
    public class someType2 {}
}
