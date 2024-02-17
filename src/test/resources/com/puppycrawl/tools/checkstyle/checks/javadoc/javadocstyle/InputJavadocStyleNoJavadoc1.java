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

public class InputJavadocStyleNoJavadoc1
{
    public int i1;
    protected int i2;
    int i3;
    private int i4;

    public void foo1() {}
    protected void foo2() {}
    void foo3() {}
    private void foo4() {}

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

        public void foo1() {}
        protected void foo2() {}
        void foo3() {}
        private void foo4() {}
    }

    private class PrivateInner {
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

class PackageClass {
    public int i1;
    protected int i2;
    int i3;
    private int i4;

    public void foo1() {}
    protected void foo2() {}
    void foo3() {}
    private void foo4() {}

    public class PublicInner {
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

    class PackageInner {
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
