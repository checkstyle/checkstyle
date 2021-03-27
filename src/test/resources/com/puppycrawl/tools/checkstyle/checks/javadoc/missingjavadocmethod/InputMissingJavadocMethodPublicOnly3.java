package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/* Config:
 * scope = "protected"
 */
public class InputMissingJavadocMethodPublicOnly3 // ignore - need javadoc
{
    private interface InnerInterface // ignore - when not relaxed about Javadoc
    {
        String CONST = "InnerInterface"; // ignore - w.n.r.a.j
        void method(); // ignore - when not relaxed about Javadoc // ok

        class InnerInnerClass // ignore - when not relaxed about Javadoc
        {
            private int mData; // ignore - when not relaxed about Javadoc

            private InnerInnerClass() // ok
            {
                final Runnable r = new Runnable() {
                    public void run() {}; // ok
                };
            }

            void method2() // ignore - when not relaxed about Javadoc // ok
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

        void method() // ignore - when not relaxed about Javadoc // ok
        {
        }
    }

    private int mSize; // ignore - when not relaxed about Javadoc
    int mLen; // ignore - when not relaxed about Javadoc
    protected int mDeer; // ignore
    public int aFreddo; // ignore

    // ignore - need Javadoc
    private InputMissingJavadocMethodPublicOnly3(int aA) // ok
    {
    }

    // ignore - need Javadoc when not relaxed
    InputMissingJavadocMethodPublicOnly3(String aA) // ok
    {
    }

    // ignore - always need javadoc
    protected InputMissingJavadocMethodPublicOnly3(Object aA) // violation
    {
    }

    // ignore - always need javadoc
    public InputMissingJavadocMethodPublicOnly3(Class<Object> aA) // violation
    {
    }

    // ignore - when not relaxed about Javadoc
    private void method(int aA) // ok
    {
    }

    // ignore - when not relaxed about Javadoc
    void method(Long aA) // ok
    {
    }

    // ignore - need javadoc
    protected void method(Class<Object> aA) // violation
    {
    }

    // ignore - need javadoc
    public void method(StringBuffer aA) // violation
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
