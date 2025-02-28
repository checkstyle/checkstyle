/*
JavadocType
scope = (default)private
excludeScope = public
authorFormat = \S
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;


public class InputJavadocType3 {
    /**
     * @param <P> some parameter
     */
    public interface InvalidParameterInJavadoc<T> {}

    /**
    *
    * {@link <T>}
    */
    protected class InnerPublic2<T> // violation 'missing @param <T> tag.'
    {
    }
}
