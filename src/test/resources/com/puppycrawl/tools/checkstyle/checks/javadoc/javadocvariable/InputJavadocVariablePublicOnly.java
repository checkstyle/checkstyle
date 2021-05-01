////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

/**
 * Config:
 * scope = private
 */
public class InputJavadocVariablePublicOnly // ignore - need javadoc
{
    private interface InnerInterface // ignore - when not relaxed about Javadoc
    {
        String CONST = "InnerInterface"; // violation - Nested types within interface are public
        void method(); // ignore - when not relaxed about Javadoc

        class InnerInnerClass // ignore - when not relaxed about Javadoc
        {
            private int mData; // ignore - when not relaxed about Javadoc // violation

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
        private int mDiff; // ignore - when not relaxed about Javadoc // violation

        void method() // ignore - when not relaxed about Javadoc
        {
        }
    }

    private int mSize; // ignore - when not relaxed about Javadoc // violation
    int mLen; // ignore - when not relaxed about Javadoc // violation
    protected int mDeer; // ignore // violation
    public int aFreddo; // ignore // violation

    // ignore - need Javadoc
    private InputJavadocVariablePublicOnly(int aA)
    {
    }

    // ignore - need Javadoc when not relaxed
    InputJavadocVariablePublicOnly(String aA)
    {
    }

    // ignore - always need javadoc
    protected InputJavadocVariablePublicOnly(Object aA)
    {
    }

    // ignore - always need javadoc
    public InputJavadocVariablePublicOnly(Class<Object> aA)
    {
    }

    // ignore - when not relaxed about Javadoc
    private void method(int aA)
    {
    }

    // ignore - when not relaxed about Javadoc
    void method(Long aA)
    {
    }

    // ignore - need javadoc
    protected void method(Class<Object> aA)
    {
    }

    // ignore - need javadoc
    public void method(StringBuffer aA)
    {
    }


    /**
       A param tag should not be required here when relaxed about Javadoc.
       Writing a little documentation should not be worse than not
       writing any documentation at all.
     */
    private void method(String aA)
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
}
