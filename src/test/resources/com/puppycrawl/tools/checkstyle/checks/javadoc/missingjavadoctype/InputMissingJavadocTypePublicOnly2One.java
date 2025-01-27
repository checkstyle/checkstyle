/*
MissingJavadocType
scope = protected
excludeScope = (default)null
skipAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

public class InputMissingJavadocTypePublicOnly2One // violation
{
    private interface InnerInterface
    {
        String CONST = "InnerInterface"; // ignore - w.n.r.a.j
        void method(); // ignore - when not relaxed about Javadoc

        class InnerInnerClass
        {
            private int mData; // ignore - when not relaxed about Javadoc

            private InnerInnerClass()
            {
                final Runnable r = new Runnable() {
                    public void run() {};
                };
            }

            void method2() // ignore - when not relaxed about Javadoc
            {
                final Runnable r = new Runnable() {
                    public void run() {};
                };
            }
        }
    }

    private class InnerClass
    {
        private int mDiff; // ignore - when not relaxed about Javadoc

        void method() // ignore - when not relaxed about Javadoc
        {
        }
    }
}
