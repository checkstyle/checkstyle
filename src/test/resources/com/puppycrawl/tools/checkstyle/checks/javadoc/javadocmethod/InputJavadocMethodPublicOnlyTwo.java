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

public class InputJavadocMethodPublicOnlyTwo {
    /**
       A param tag should not be required here when relaxed about Javadoc.
       Writing a little documentation should not be worse than not
       writing any documentation at all.
     */
    private void method(String aA) // violation 'Expected @param tag for 'aA'.'
    {
    }

    /**
       This inner class has no author tag, which is OK.
     */
    public class InnerWithoutAuthor
    {

    }

    /** {@inheritDoc} */
    public String toString()
    {
        return super.toString();
    }

    @Deprecated @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    public Thread anonymousClassInMethod() {
        return new Thread() {
            @Override
            public void run() {
                privateMethod(null, null);
            }

            /**
             * Javadoc
             */
            private String privateMethod(String a, String b) {
                return null;
            }
        };
    }

    private final Thread anonymousClassInField = new Thread() {
        @Override
        public void run() {
            publicMethod(null, null);
        }

        /**
         * Javadoc
         */
        public String publicMethod(String a, String b) {
            return null;
        }
    };
}
