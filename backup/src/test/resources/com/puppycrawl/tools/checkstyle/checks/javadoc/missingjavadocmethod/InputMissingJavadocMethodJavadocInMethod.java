/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF , CTOR_DEF , ANNOTATION_FIELD_DEF , COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/* Config:
 * scope = "private"
 */
public class InputMissingJavadocMethodJavadocInMethod {
    public void foo1() { } // violation

    @Deprecated // violation
    public void foo2() { }

    @Deprecated // violation
    /** */
    public void foo3() { }

    public void foo4() { /** */ } // violation

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
