package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

/*
 * Config:
 * scope = package
 * excludeScope = null
 * skipAnnotations = Generated
 */

/**
   Checks javadoc scoping for inner classes.

   Once the Javadoc Check Scope has been left,
   all inner elements should not be reported as violation,
   even if they belong to the checkscope if isolated.
 */
public class InputMissingJavadocTypeScopeInnerClasses1 // ok
{
    public class InnerPublic // violation
    {
        protected class InnerProtected // violation
        {
            class InnerPackage // violation
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
