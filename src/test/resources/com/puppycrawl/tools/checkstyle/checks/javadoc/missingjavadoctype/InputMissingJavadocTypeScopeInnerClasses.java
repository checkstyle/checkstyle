package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

/**
   Checks javadoc scoping for inner classes.

   Once the Javadoc Check Scope has been left,
   all inner elements should not be reported as violation,
   even if they belong to the checkscope if isolated.
 */
public class InputMissingJavadocTypeScopeInnerClasses
{
    public class InnerPublic
    {
        protected class InnerProtected
        {
            class InnerPackage
            {
                private class InnerPrivate
                {
                    // no javadoc required for package scope
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
}
