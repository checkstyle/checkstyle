/*
TypeName
format = (default)^[A-Z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.typename;

class inputHeaderClass2 { // violation

    public interface inputHeaderInterface {}; // violation
//comment
    public enum inputHeaderEnum { one, two }; // violation

    public @interface inputHeaderAnnotation {}; // violation

}

public class InputTypeName2 {}
