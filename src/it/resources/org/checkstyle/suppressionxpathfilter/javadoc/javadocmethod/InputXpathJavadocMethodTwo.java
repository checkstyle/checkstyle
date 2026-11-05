/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
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
 */
public class InputXpathJavadocMethodTwo {

    /**
     * Needs a param tag for x.
     *
     * @return identity
     */
    public int checkParam(int x) { // violation 'Expected @param tag for 'x'.'
        return x;
    }

}
