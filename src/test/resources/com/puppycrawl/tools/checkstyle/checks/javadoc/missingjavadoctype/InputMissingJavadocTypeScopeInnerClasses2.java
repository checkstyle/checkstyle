/*
MissingJavadocType
scope = (default)public
excludeScope = (default)null
skipAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

/**
 Checks javadoc scoping for inner classes.

 Once the Javadoc Check Scope has been left,
 all inner elements should not be reported as violation,
 even if they belong to the checkscope if isolated.
 */
public class InputMissingJavadocTypeScopeInnerClasses2 // ok
{
    public class InnerPublic // violation
    {
        protected class InnerProtected // ok
        {
            class InnerPackage // ok
            {
                private class InnerPrivate
                {
                    // no javadoc required for package scope
                    class PrivateHiddenPackage // ok
                    {
                    }

                    protected class PrivateHiddenProtected // ok
                    {
                    }

                    public class PrivateHiddenPublic // ok
                    {
                    }
                }
            }
        }
    }
}
