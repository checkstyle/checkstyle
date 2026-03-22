/*
TypeName
format = (default)^[A-Z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true
tokens = CLASS_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.typename;

class inputHeaderClass3 { // violation 'Name 'inputHeaderClass3' must match pattern'

    public interface inputHeaderInterface {};
//comment
    public enum inputHeaderEnum { one, two };

    public @interface inputHeaderAnnotation {};

}

public class InputTypeName3 {}
