/*
JavadocType
scope = public
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
   Checks javadoc scoping for inner classes.

   Once the Javadoc Check Scope has been left,
   all inner elements should not be reported as violation,
   even if they belong to the checkscope if isolated.

   @author lkuehne
 */
public class InputJavadocTypeScopeInnerClasses1 // ok
{
    public class InnerPublic
    {
        protected class InnerProtected
        {
            class InnerPackage
            {
                private class InnerPrivate
                {

                    class PrivateHiddenPackage
                    {
                    }

                    protected class PrivateHiddenProtected
                    {
                    }

                    public class PrivateHiddenPublic
                    {
                    }
                }
            }
        }
    }
    /** */
    protected class InnerPublic2<T> // ok
    {
    }
}
