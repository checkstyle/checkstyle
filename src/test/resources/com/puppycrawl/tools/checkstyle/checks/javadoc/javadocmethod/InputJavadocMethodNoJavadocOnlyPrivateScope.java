/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = private, package, public
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodNoJavadocOnlyPrivateScope //comment test
{
    public int i1;
    protected int i2;
    int i3;
    private int i4;

    public void foo1() {}
    protected void foo2() {}
    void foo3() {}
    private void foo4() {}

    // violation below 'Unused Javadoc tag.'
    /** @return wrong, in scope */
    public void foo5() {}
    /** @return correct, out of scope */
    protected void foo6() {}
    // violation below 'Unused Javadoc tag.'
    /** @return wrong, in scope */
    void foo7() {}
    // violation below 'Unused Javadoc tag.'
    /** @return wrong, in scope */
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

