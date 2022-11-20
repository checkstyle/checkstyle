/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = \tpublic
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodAccessModifier {
    class classOne {

    }

    class classTwo {

    }

    /**
     * @return
     */
    public String method1() { // violation '@return tag should be present and have description'
        return "arbitrary_string";
    }
}