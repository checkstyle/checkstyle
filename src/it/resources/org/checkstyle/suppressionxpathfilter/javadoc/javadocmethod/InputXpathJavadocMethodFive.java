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
public class InputXpathJavadocMethodFive {

    /**
     * Needs a throws tag.
     */
    public void bar() {
        throw new org.apache.tools.ant.BuildException(""); // violation 'Expected @throws tag'
    }

}
