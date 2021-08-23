/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = protected
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF , CTOR_DEF , ANNOTATION_FIELD_DEF , COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/* Config:
 * scope = "protected"
 */
public class InputMissingJavadocMethodNoJavadoc2 //comment test
{
    public int i1;
    protected int i2;
    int i3;
    private int i4;

    public void foo1() {} // violation
    protected void foo2() {} // violation
    void foo3() {} // ok
    private void foo4() {} // ok

    protected class ProtectedInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {} // violation
        protected void foo2() {} // violation
        void foo3() {} // ok
        private void foo4() {} // ok
    }

    class PackageInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {} // ok
        protected void foo2() {} // ok
        void foo3() {} // ok
        private void foo4() {} // ok
    }

    private class PrivateInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {} // ok
        protected void foo2() {} // ok
        void foo3() {} // ok
        private void foo4() {} // ok
    }
}

class PackageClass2 {
    public int i1;
    protected int i2;
    int i3;
    private int i4;

    public void foo1() {} // ok
    protected void foo2() {} // ok
    void foo3() {} // ok
    private void foo4() {} // ok

    public class PublicInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {} // ok
        protected void foo2() {} // ok
        void foo3() {} // ok
        private void foo4() {} // ok
    }

    protected class ProtectedInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {} // ok
        protected void foo2() {} // ok
        void foo3() {} // ok
        private void foo4() {} // ok
    }

    class PackageInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {} // ok
        protected void foo2() {} // ok
        void foo3() {} // ok
        private void foo4() {} // ok
    }

    private class PrivateInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {} // ok
        protected void foo2() {} // ok
        void foo3() {} // ok
        private void foo4() {} // ok
    }

    class IgnoredName {
        // ignore by name
        private int logger;
        // no warning, 'serialVersionUID' fields do not require Javadoc
        private static final long serialVersionUID = 0;
    }

    /**/
    void methodWithTwoStarComment() {} // ok
}
