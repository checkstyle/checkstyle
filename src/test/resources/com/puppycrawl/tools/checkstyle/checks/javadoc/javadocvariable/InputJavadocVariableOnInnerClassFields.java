/*
JavadocVariable
accessModifiers = (default)private
ignoreNamePattern = (default)null
tokens = (default)ENUM_CONSTANT_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

public class InputJavadocVariableOnInnerClassFields //comment test
{
    public int i1; // violation, 'Missing a Javadoc comment'
    protected int i2; // violation, 'Missing a Javadoc comment'
    int i3; // violation, 'Missing a Javadoc comment'
    private int i4; // violation, 'Missing a Javadoc comment'

    public void foo1() {}
    protected void foo2() {}
    void foo3() {}
    private void foo4() {}

    protected class ProtectedInner {
        public int i1; // violation, 'Missing a Javadoc comment'
        protected int i2; // violation, 'Missing a Javadoc comment'
        int i3; // violation, 'Missing a Javadoc comment'
        private int i4; // violation, 'Missing a Javadoc comment'

        public void foo1() {}
        protected void foo2() {}
        void foo3() {}
        private void foo4() {}
    }

    class PackageInner {
        public int i1; // violation, 'Missing a Javadoc comment'
        protected int i2; // violation, 'Missing a Javadoc comment'
        int i3; // violation, 'Missing a Javadoc comment'
        private int i4; // violation, 'Missing a Javadoc comment'

        public void foo1() {}
        protected void foo2() {}
        void foo3() {}
        private void foo4() {}
    }

    private class PrivateInner {
        public int i1; // violation, 'Missing a Javadoc comment'
        protected int i2; // violation, 'Missing a Javadoc comment'
        int i3; // violation, 'Missing a Javadoc comment'
        private int i4; // violation, 'Missing a Javadoc comment'

        public void foo1() {}
        protected void foo2() {}
        void foo3() {}
        private void foo4() {}
    }
}
