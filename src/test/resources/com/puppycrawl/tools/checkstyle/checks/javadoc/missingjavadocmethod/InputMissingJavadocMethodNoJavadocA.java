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
public class InputMissingJavadocMethodNoJavadocA //comment test
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
