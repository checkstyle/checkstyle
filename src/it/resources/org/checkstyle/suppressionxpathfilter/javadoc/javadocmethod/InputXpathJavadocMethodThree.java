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
public class InputXpathJavadocMethodThree {

    /**
     * Needs a param tag for T.
     *
     * @param x the input
     * @return hashcode of x
     */
    public <T> int checkTypeParam(T x) { // violation 'Expected @param tag for '<T>'.'
        return x.hashCode();
    }

}
