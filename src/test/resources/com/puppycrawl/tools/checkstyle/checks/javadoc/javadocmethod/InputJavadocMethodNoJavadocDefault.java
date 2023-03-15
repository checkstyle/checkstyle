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

public class InputJavadocMethodNoJavadocDefault //comment test
{
    public int i1;
    protected int i2;
    int i3;
    private int i4;

    public void foo1() {}
    protected void foo2() {}
    void foo3() {}
    private void foo4() {}

    /** @return wrong, in scope */ // violation
    public void foo5() {}
    /** @return wrong, in scope */ // violation
    protected void foo6() {}
    /** @return wrong, in scope */ // violation
    void foo7() {}
    /** @return wrong, in scope */ // violation
    private void foo8() {}

    protected class ProtectedInner { // ignored
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {}
        protected void foo2() {}
        void foo3() {}
        private void foo4() {}
    }

    class PackageInner { // ignored
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {}
        protected void foo2() {}
        void foo3() {}
        private void foo4() {}
    }

    private class PrivateInner { // ignored
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {}
        protected void foo2() {}
        void foo3() {}
        private void foo4() {}
    }
}

class PackageClass { // ignored
    public int i1;
    protected int i2;
    int i3;
    private int i4;

    public void foo1() {}
    protected void foo2() {}
    void foo3() {}
    private void foo4() {}

    public class PublicInner { // ignored
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {}
        protected void foo2() {}
        void foo3() {}
        private void foo4() {}
    }

    protected class ProtectedInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {}
        protected void foo2() {}
        void foo3() {}
        private void foo4() {}
    }

    class PackageInner { // ignored
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {}
        protected void foo2() {}
        void foo3() {}
        private void foo4() {}
    }

    private class PrivateInner { // ignored
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {}
        protected void foo2() {}
        void foo3() {}
        private void foo4() {}
    }

    class IgnoredName { // ignored
        // ignore by name
        private int logger;
        // no warning, 'serialVersionUID' fields do not require Javadoc
        private static final long serialVersionUID = 0;
    }

    /**/
    void methodWithTwoStarComment() {} // ignored
}
