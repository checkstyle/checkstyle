/*
JavadocStyle
scope = (default)private
excludeScope = (default)null
checkFirstSentence = (default)true
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
checkEmptyJavadoc = (default)false
checkHtml = (default)true
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleNoJavadoc // ok
{
    public int i1; // ok
    protected int i2; // ok
    int i3; // ok
    private int i4; // ok

    public void foo1() {} // ok
    protected void foo2() {} // ok
    void foo3() {} // ok
    private void foo4() {} // ok

    protected class ProtectedInner { // ok
        public int i1; // ok
        protected int i2; // ok
        int i3; // ok
        private int i4; // ok

        public void foo1() {} // ok
        protected void foo2() {} // ok
        void foo3() {} // ok
        private void foo4() {} // ok
    }

    class PackageInner { // ok
        public int i1; // ok
        protected int i2; // ok
        int i3; // ok
        private int i4; // ok

        public void foo1() {} // ok
        protected void foo2() {} // ok
        void foo3() {} // ok
        private void foo4() {} // ok
    }

    private class PrivateInner { // ok
        public int i1; // ok
        protected int i2; // ok
        int i3; // ok
        private int i4; // ok

        public void foo1() {} // ok
        protected void foo2() {} // ok
        void foo3() {} // ok
        private void foo4() {} // ok
    }
}

class PackageClass { // ok
    public int i1; // ok
    protected int i2; // ok
    int i3; // ok
    private int i4; // ok

    public void foo1() {} // ok
    protected void foo2() {} // ok
    void foo3() {} // ok
    private void foo4() {} // ok

    public class PublicInner { // ok
        public int i1; // ok
        protected int i2; // ok
        int i3; // ok
        private int i4; // ok

        public void foo1() {} // ok
        protected void foo2() {} // ok
        void foo3() {} // ok
        private void foo4() {} // ok
    }

    protected class ProtectedInner { // ok
        public int i1; // ok
        protected int i2; // ok
        int i3; // ok
        private int i4; // ok

        public void foo1() {} // ok
        protected void foo2() {} // ok
        void foo3() {} // ok
        private void foo4() {} // ok
    }

    class PackageInner { // ok
        public int i1; // ok
        protected int i2; // ok
        int i3; // ok
        private int i4; // ok

        public void foo1() {} // ok
        protected void foo2() {} // ok
        void foo3() {} // ok
        private void foo4() {} // ok
    }

    private class PrivateInner { // ok
        public int i1; // ok
        protected int i2; // ok
        int i3; // ok
        private int i4; // ok

        public void foo1() {} // ok
        protected void foo2() {} // ok
        void foo3() {} // ok
        private void foo4() {} // ok
    }

    class IgnoredName { // ok
        // ignore by name
        private int logger; // ok
        // no warning, 'serialVersionUID' fields do not require Javadoc
        private static final long serialVersionUID = 0; // ok
    }

    /**/
    void methodWithTwoStarComment() {} // ok
}
