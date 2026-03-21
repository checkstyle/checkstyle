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
public class InputMissingJavadocMethodNoJavadoc //comment test
{
    public int i1;
    protected int i2;
    int i3;
    private int i4;

    public void foo1() {} // violation
    protected void foo2() {} // violation
    void foo3() {} // violation
    private void foo4() {} // violation

    protected class ProtectedInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {} // violation
        protected void foo2() {} // violation
        void foo3() {} // violation
        private void foo4() {} // violation
    }

    class PackageInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {} // violation
        protected void foo2() {} // violation
        void foo3() {} // violation
        private void foo4() {} // violation
    }

    private class PrivateInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {} // violation
        protected void foo2() {} // violation
        void foo3() {} // violation
        private void foo4() {} // violation
    }
}

class PackageClass {
    public int i1;
    protected int i2;
    int i3;
    private int i4;

    public void foo1() {} // violation
    protected void foo2() {} // violation
    void foo3() {} // violation
    private void foo4() {} // violation

    public class PublicInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {} // violation
        protected void foo2() {} // violation
        void foo3() {} // violation
        private void foo4() {} // violation
    }

    protected class ProtectedInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {} // violation
        protected void foo2() {} // violation
        void foo3() {} // violation
        private void foo4() {} // violation
    }

    class PackageInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {} // violation
        protected void foo2() {} // violation
        void foo3() {} // violation
        private void foo4() {} // violation
    }

    private class PrivateInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {} // violation
        protected void foo2() {} // violation
        void foo3() {} // violation
        private void foo4() {} // violation
    }

    class IgnoredName {
        // ignore by name
        private int logger;
        // no warning, 'serialVersionUID' fields do not require Javadoc
        private static final long serialVersionUID = 0;
    }

    /**/
    void methodWithTwoStarComment() {} // violation
}
