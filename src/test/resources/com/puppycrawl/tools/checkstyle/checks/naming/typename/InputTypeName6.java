/*
TypeName
format = (default)^[A-Z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true
tokens = ANNOTATION_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.typename;

class inputHeaderClass6 {

    public interface inputHeaderInterface {};
//comment
    public enum inputHeaderEnum { one, two };

    public @interface inputHeaderAnnotation {}; // violation 'Name 'inputHeaderAnnotation' must match pattern'

}

public class InputTypeName6 {}
