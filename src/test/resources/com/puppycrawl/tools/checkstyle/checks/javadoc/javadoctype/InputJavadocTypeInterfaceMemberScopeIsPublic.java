/*
JavadocType
scope = public
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

public interface InputJavadocTypeInterfaceMemberScopeIsPublic {

    // violation below 'Unused @param tag for '<T>'.'
    /** @param <T> unused */
    enum Enum {

    }

    // violation below 'Unused @param tag for '<T>'.'
    /** @param <T> unused */
    class Class {

    }

}
