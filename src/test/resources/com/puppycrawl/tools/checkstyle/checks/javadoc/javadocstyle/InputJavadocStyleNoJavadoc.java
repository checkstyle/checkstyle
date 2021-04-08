package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

/*
 * Config:
 * scope = private
 * excludeScope = null
 * checkFirstSentence = true
 * endOfSentenceFormat = "([.?!][ \t\n\r\f<])|([.?!]$)"
 * checkEmptyJavadoc = false
 * checkHtml = true
 */
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
