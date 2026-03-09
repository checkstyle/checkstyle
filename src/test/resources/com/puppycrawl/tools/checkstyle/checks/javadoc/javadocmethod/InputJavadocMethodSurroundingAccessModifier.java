/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
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
     * @return
     */
    public void foo5() {
    }

    /**
     * @return
     */
    protected void foo6() {
    }

    /**
     * @return
     */
    void foo7() {
    }

    /**
     * @return
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

