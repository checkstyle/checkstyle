/*
MissingJavadocMethod
ignoreMethodsWithImplementation = (default)false
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = (default)public
allowMissingPropertyJavadoc = true
tokens = (default)METHOD_DEF , CTOR_DEF , ANNOTATION_FIELD_DEF , COMPACT_CTOR_DEF

*/
// java17
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;
public class InputMissingJavadocMethodBasic {
    public void validAssign(String result) {// violation 'Missing a Javadoc comment'
        result = switch ("in") {
            case "correct" -> "true";
            default -> "also correct";
        };
    }
}
