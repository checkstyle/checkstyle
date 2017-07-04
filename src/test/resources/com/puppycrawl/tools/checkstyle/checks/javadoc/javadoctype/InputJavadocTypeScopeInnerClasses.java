////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
   Checks javadoc scoping for inner classes.

   Once the Javadoc Check Scope has been left,
   all inner elements should not be reported as error,
   even if they belong to the checkscope if isolated.

   @author lkuehne
 */
public class InputJavadocTypeScopeInnerClasses
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
