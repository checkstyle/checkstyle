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

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodThrowsBeforeParam {
    /**
     * @throws Exception if processing fails
     * @param Exception the value to process
     */
    public void process(int Exception) throws Exception {
    }

    /**
     * @exception Exception if processing fails
     * @param Exception the value to process
     */
    public void processAgain(int Exception) throws Exception {
    }
}
