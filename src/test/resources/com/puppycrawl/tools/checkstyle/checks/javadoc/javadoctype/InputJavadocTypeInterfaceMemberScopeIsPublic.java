/*
JavadocType
scope = public
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)BLOCK_COMMENT_BEGIN


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
