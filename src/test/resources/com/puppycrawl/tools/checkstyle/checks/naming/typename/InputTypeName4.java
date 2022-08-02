/*
TypeName
format = (default)^[A-Z][a-zA-Z\d]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true
tokens = INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.typename;

class inputHeaderClass4 {

    public interface inputHeaderInterface {}; // violation
//comment
    public enum inputHeaderEnum { one, two };

    public @interface inputHeaderAnnotation {};

}

public class InputTypeName4 {}
