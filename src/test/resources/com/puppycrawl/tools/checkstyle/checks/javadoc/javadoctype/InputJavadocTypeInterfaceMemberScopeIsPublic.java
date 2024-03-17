/*
JavadocType
scope = public
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

public interface InputJavadocTypeInterfaceMemberScopeIsPublic {

    /** @param <T> unused */  // violation 'Unused @param tag for '<T>'.'
    enum Enum {

    }

    /** @param <T> unused */  // violation 'Unused @param tag for '<T>'.'
    class Class {

    }

}
