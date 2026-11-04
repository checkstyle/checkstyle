/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package org.checkstyle.suppressionxpathfilter.javadoc.javadocmethod;

/**
 * Config:
 * validateThrows = true
 */
public class InputXpathJavadocMethodFour {

    /**
     * Needs a throws tag.
     */
    public void foo() throws Exception { // violation 'Expected @throws tag for 'Exception'.'

    }

}
