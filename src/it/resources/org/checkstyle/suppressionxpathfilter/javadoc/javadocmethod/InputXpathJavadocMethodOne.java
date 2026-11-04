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
public class InputXpathJavadocMethodOne {

    /** {@inheritDoc} */
    public void inheritableMethod() {
        // may be inherited and overridden
    }

    /** {@inheritDoc} */
    private void uninheritableMethod() { // violation 'Invalid use of the '@inheritDoc' tag.'
        // not possible to be inherited
    }

}
