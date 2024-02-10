/*
JavadocType
scope = (default)private
excludeScope = (default)null
authorFormat = \S
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

public class InputJavadocType1 {

    /**<p>Some Javadoc. //warn
     * <p> //warn
     * <p><p> //warn
     * <p>/^WARN/   Some Javadoc.<p>*/
    class InnerInputCorrectJavaDocParagraphCheck {
    }
}
