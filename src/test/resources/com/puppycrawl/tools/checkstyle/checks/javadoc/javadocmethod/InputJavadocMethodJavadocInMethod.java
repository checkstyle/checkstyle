/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodJavadocInMethod {
    public void foo1() { }

    @Deprecated
    public void foo2() { }

    @Deprecated
    /** */
    public void foo3() { }

    public void foo4() { /** */ }

    @Deprecated
    public void foo5() { /** */ }

    @Deprecated
    /** */
    public void foo6() { /** */ }

    /** */
    public void foo7() { /** */ }

    /** */
    @Deprecated
    public void foo8() { /** */ }

    /** */
    @Deprecated
    /** */
    public void foo9() { /** */ }
}
