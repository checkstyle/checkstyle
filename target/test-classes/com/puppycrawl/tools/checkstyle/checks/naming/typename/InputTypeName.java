/*
TypeName
format = ^inputHe
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.typename;

class inputHeaderClass {

    public interface inputHeaderInterface {};
//comment
    public enum inputHeaderEnum { one, two };

    public @interface inputHeaderAnnotation {};

}

public class InputTypeName {} // violation 'Name 'InputTypeName' must match pattern'
