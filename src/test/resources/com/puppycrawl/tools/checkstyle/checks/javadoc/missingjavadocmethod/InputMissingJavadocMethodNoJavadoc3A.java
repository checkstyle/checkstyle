/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = private
excludeScope = protected
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
ignoreMethodsWithImplementation = (default)false
tokens = (default)METHOD_DEF , CTOR_DEF , ANNOTATION_FIELD_DEF , COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/* Config:
 * scope = "private"
 * excludeScope = "protected"
 */
public class InputMissingJavadocMethodNoJavadoc3A //comment test
{
    public int i1;
    protected int i2;
    int i3;
    private int i4;

    public void foo1() {} // violation 'Missing a Javadoc comment.'
    protected void foo2() {}
    void foo3() {} // violation 'Missing a Javadoc comment.'
    private void foo4() {} // violation 'Missing a Javadoc comment.'

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

    class PackageInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {} // violation 'Missing a Javadoc comment.'
        protected void foo2() {}
        void foo3() {} // violation 'Missing a Javadoc comment.'
        private void foo4() {} // violation 'Missing a Javadoc comment.'
    }

    private class PrivateInner {
        public int i1;
        protected int i2;
        int i3;
        private int i4;

        public void foo1() {} // violation 'Missing a Javadoc comment.'
        protected void foo2() {}
        void foo3() {} // violation 'Missing a Javadoc comment.'
        private void foo4() {} // violation 'Missing a Javadoc comment.'
    }
}
