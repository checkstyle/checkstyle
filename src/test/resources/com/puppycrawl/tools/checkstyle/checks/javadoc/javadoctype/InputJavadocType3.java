/*
JavadocType
scope = (default)private
excludeScope = public
authorFormat = \S
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;


public class InputJavadocType3 {
    /**
     * @param <P> some parameter
     */
    public interface InvalidParameterInJavadoc<T> {}

    /**
    *
    * @link <T>
    */
    protected class InnerPublic2<T> // violation 'missing @param <T> tag.'
    {
    }
}
