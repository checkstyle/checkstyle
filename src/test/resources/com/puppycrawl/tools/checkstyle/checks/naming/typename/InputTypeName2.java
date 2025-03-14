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

class inputHeaderClass2 { // violation 'Name 'inputHeaderClass2' must match pattern'
    // violation below 'Name 'inputHeaderInterface' must match pattern'
    public interface inputHeaderInterface {};
    // violation below 'Name 'inputHeaderEnum' must match pattern'
    public enum inputHeaderEnum { one, two };
    // violation below 'Name 'inputHeaderAnnotation' must match pattern'
    public @interface inputHeaderAnnotation {};

}

public class InputTypeName2 {}
