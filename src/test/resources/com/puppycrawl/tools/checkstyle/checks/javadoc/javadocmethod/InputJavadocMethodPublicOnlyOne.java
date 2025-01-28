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

public class InputJavadocMethodPublicOnlyOne // ignore - need javadoc
{
    private interface InnerInterface // ignore - when not relaxed about Javadoc
    {
        String CONST = "InnerInterface"; // ignore - w.n.r.a.j
        void method(); // ignore - when not relaxed about Javadoc

        class InnerInnerClass // ignore - when not relaxed about Javadoc
        {
            private int mData; // ignore - when not relaxed about Javadoc

            private InnerInnerClass()
            {
                final Runnable r = new Runnable() {
                        public void run() {};
                    };
            }

            void method2() // ignore - when not relaxed about Javadoc
            {
                final Runnable r = new Runnable() {
                        public void run() {};
                    };
            }
        }
    }

    private class InnerClass // ignore
    {
        private int mDiff; // ignore - when not relaxed about Javadoc

        void method() // ignore - when not relaxed about Javadoc
        {
        }
    }

    private int mSize; // ignore - when not relaxed about Javadoc
    int mLen; // ignore - when not relaxed about Javadoc
    protected int mDeer; // ignore
    public int aFreddo; // ignore

    // ignore - need Javadoc
<<<<<<< Updated upstream:src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod/InputJavadocMethodPublicOnly.java
    private InputJavadocMethodPublicOnly(int aA)
=======
    private InputJavadocMethodPublicOnlyOne(int aA)
>>>>>>> Stashed changes:src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod/InputJavadocMethodPublicOnlyOne.java
    {
    }

    // ignore - need Javadoc when not relaxed
<<<<<<< Updated upstream:src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod/InputJavadocMethodPublicOnly.java
    InputJavadocMethodPublicOnly(String aA)
=======
    InputJavadocMethodPublicOnlyOne(String aA)
>>>>>>> Stashed changes:src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod/InputJavadocMethodPublicOnlyOne.java
    {
    }

    // ignore - always need javadoc
<<<<<<< Updated upstream:src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod/InputJavadocMethodPublicOnly.java
    protected InputJavadocMethodPublicOnly(Object aA)
=======
    protected InputJavadocMethodPublicOnlyOne(Object aA)
>>>>>>> Stashed changes:src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod/InputJavadocMethodPublicOnlyOne.java
    {
    }

    // ignore - always need javadoc
<<<<<<< Updated upstream:src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod/InputJavadocMethodPublicOnly.java
    public InputJavadocMethodPublicOnly(Class<Object> aA)
=======
    public InputJavadocMethodPublicOnlyOne(Class<Object> aA)
>>>>>>> Stashed changes:src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod/InputJavadocMethodPublicOnlyOne.java
    {
    }

    /** Here should be an error, In scope */
    private void method(int aA) // violation 'Expected @param tag for 'aA'.'
    {
    }

    /** Here should be an error, In scope */
    void method(Long aA) // violation 'Expected @param tag for 'aA'.'
    {
    }

    /** Here should be an error, In scope */
    protected void method(Class<Object> aA) // violation 'Expected @param tag for 'aA'.'
    {
    }

    /** Here should be an error, In scope */
    public void method(StringBuffer aA) // violation 'Expected @param tag for 'aA'.'
    {
    }
<<<<<<< Updated upstream:src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod/InputJavadocMethodPublicOnly.java


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
=======
>>>>>>> Stashed changes:src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod/InputJavadocMethodPublicOnlyOne.java
}
