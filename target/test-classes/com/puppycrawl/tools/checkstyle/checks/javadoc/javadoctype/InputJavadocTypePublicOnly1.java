/*
JavadocType
scope = protected
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

public class InputJavadocTypePublicOnly1
{
    private interface InnerInterface
    {
        String CONST = "InnerInterface";
        void method();

        class InnerInnerClass
        {
            private int mData;

            private InnerInnerClass()
            {
                final Runnable r = new Runnable() {
                        public void run() {};
                    };
            }

            void method2()
            {
                final Runnable r = new Runnable() {
                        public void run() {};
                    };
            }
        }
    }

    private class InnerClass
    {
        private int mDiff;

        void method()
        {
        }
    }

    private int mSize;
    int mLen;
    protected int mDeer;
    public int aFreddo;
}
