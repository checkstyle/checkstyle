/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
ignoreMethodsWithImplementation = (default)false
tokens = (default)METHOD_DEF , CTOR_DEF , ANNOTATION_FIELD_DEF , COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/* Config:
 * scope = "private"
 */
public class InputMissingJavadocMethodPublicOnly
{
    private interface InnerInterface
    {
        String CONST = "InnerInterface";
        void method(); // violation 'Missing a Javadoc comment.'

        class InnerInnerClass
        {
            private int mData;

            private InnerInnerClass() // violation 'Missing a Javadoc comment.'
            {
                final Runnable r = new Runnable() {
                        public void run() {};
                    };
            }

            void method2() // violation 'Missing a Javadoc comment.'
            {
                final Runnable r = new Runnable() {
                        public void run() {};
                    };
            }
        }
    }

    private class InnerClass
    {
        private int mDiff;

        void method() // violation 'Missing a Javadoc comment.'
        {
        }
    }

    private int mSize;
    int mLen;
    protected int mDeer;
    public int aFreddo;

    private InputMissingJavadocMethodPublicOnly(int aA) // violation 'Missing a Javadoc comment.'
    {
    }

    InputMissingJavadocMethodPublicOnly(String aA) // violation 'Missing a Javadoc comment.'
    {
    }

    protected InputMissingJavadocMethodPublicOnly(Object aA)
    // violation above 'Missing a Javadoc comment.'
    {
    }

    public InputMissingJavadocMethodPublicOnly(Class<Object> aA)
    // violation above 'Missing a Javadoc comment.'
    {
    }

    private void method(int aA) // violation 'Missing a Javadoc comment.'
    {
    }

    void method(Long aA) // violation 'Missing a Javadoc comment.'
    {
    }

    protected void method(Class<Object> aA) // violation 'Missing a Javadoc comment.'
    {
    }

    public void method(StringBuffer aA) // violation 'Missing a Javadoc comment.'
    {
    }

    /**
     * A param tag should not be required here when relaxed about Javadoc.
     * Writing a little documentation should not be worse than not
     * writing any documentation at all.
     */
    private void method(String aA)
    {
    }
    /**
     * This inner class has no author tag, which is OK.
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
}
