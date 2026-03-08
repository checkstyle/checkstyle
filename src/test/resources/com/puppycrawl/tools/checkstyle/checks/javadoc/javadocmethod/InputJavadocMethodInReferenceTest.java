/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

/** */
public class InputJavadocMethodInReferenceTest {
    /**
     * @param x description
     */
    public int invalid_param;

    /**
     * @param x description
     */
    public class InvalidParam { }

    // violation 2 lines below 'Unused @param tag for 'x'.'
    /**
     * @param x description
     */
    // violation below 'Expected @param tag for 'a'.'
    public void param_name_not_found(int a) { }

    /**
     * @param <X> description
     */
    public class typaram_name_not_found { }

    /**
     * @see Object#tooStrong()
     */
    public void ref_not_found() { }

    /**
     * @return x description
     */
    public int invalid_return;

    // violation 2 lines below 'Unused Javadoc tag.'
    /**
     * @return x description
     */
    public void invalid_return() {}

    /**
     * @throws Exception description
     */
    public void exception_not_thrown() { }

    /**
     * @param <T> throwable
     * @throws T description
     */
    public <T extends Throwable> void valid_throws_generic() throws T { }

    /**
     * {@link java.util.List<String>}
     * {@link java.util.List<String>#equals}
     * {@link not.Found<String>}
     * @see java.util.List<String>
     * @see java.util.List<String>#equals
     * @see not.Found<String>
     */
    public void invalid_type_args() { }

    /**
     * {@link java.lang.String[]}
     * {@link java.lang.String[]#equals}
     * {@link not.Found[]}
     * @see java.lang.String[]
     * @see java.lang.String[]#equals
     * @see not.Found[]
     */
    public void invalid_array_types() { }
}
