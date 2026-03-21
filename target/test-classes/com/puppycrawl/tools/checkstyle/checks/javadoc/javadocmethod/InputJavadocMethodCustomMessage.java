/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF
message.javadoc.return.expected = @return tag should be present and have description :)
message.javadoc.expectedTag = Expected {0} tag for ''{1}'' :)
message.javadoc.unusedTag = Unused {0} tag for ''{1}'' :)

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodCustomMessage {

    /** missing return **/
    int method3() { // violation '@return tag should be present and have description :)'
        return 3;
    }

    /** @param unused asd **/ // violation 'Unused @param tag for 'unused' :)'
    void method2() {
    }

    /**
     * Missing param tag.
     */
    void method3(int a) { // violation 'Expected @param tag for 'a' :)'
    }
}
