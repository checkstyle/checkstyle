/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodJavadocInMethod {
    public void foo1() { } // ok

    @Deprecated
    public void foo2() { } // ok

    @Deprecated
    /** */
    public void foo3() { } // ok

    public void foo4() { /** */ } // ok

    @Deprecated
    public void foo5() { /** */ } // ok

    @Deprecated
    /** */
    public void foo6() { /** */ } // ok

    /** */
    public void foo7() { /** */ } // ok

    /** */
    @Deprecated
    public void foo8() { /** */ } // ok

    /** */
    @Deprecated
    /** */
    public void foo9() { /** */ } // ok
}
