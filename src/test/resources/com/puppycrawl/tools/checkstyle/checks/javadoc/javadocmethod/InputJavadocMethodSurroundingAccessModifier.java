/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodSurroundingAccessModifier //comment test
{
    public int i1;
    protected int i2;
    int i3;
    private int i4;

    public void foo1() {
    }

    protected void foo2() {
    }

    void foo3() {
    }

    private void foo4() {
    }

    /**
     * @return // ok
     */
    public void foo5() {
    }

    /**
     * @return // ok
     */
    protected void foo6() {
    }

    /**
     * @return // ok
     */
    void foo7() {
    }

    /**
     * @return // ok
     */
    private void foo8() {
    }

    protected class ProtectedInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {
        }

        protected void foo2() {
        }

        void foo3() {
        }

        private void foo4() {
        }
    }

    class PackageInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {
        }

        protected void foo2() {
        }

        void foo3() {
        }

        private void foo4() {
        }
    }

    private class PrivateInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {
        }

        protected void foo2() {
        }

        void foo3() {
        }

        private void foo4() {
        }
    }
}

class PackageClass2 {
    public int i1;
    protected int i2;
    int i3;
    private int i4;

    public void foo1() {
    }

    protected void foo2() {
    }

    void foo3() {
    }

    private void foo4() {
    }

    public class PublicInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {
        }

        protected void foo2() {
        }

        void foo3() {
        }

        private void foo4() {
        }
    }

    protected class ProtectedInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {
        }

        protected void foo2() {
        }

        void foo3() {
        }

        private void foo4() {
        }
    }

    class PackageInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {
        }

        protected void foo2() {
        }

        void foo3() {
        }

        private void foo4() {
        }
    }

    private class PrivateInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {
        }

        protected void foo2() {
        }

        void foo3() {
        }

        private void foo4() {
        }
    }

    class IgnoredName {
        // ignore by name
        private int logger;
        // no warning, 'serialVersionUID' fields do not require Javadoc
        private static final long serialVersionUID = 0;
    }

    /**/
    void methodWithTwoStarComment() {
    }
}
