/*
MissingJavadocType
excludeScope = (default)null
scope = package
skipAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

/**
   Checks javadoc scoping for inner classes.

   Once the Javadoc Check Scope has been left,
   all inner elements should not be reported as violation,
   even if they belong to the checkscope if isolated.
 */
public class InputMissingJavadocTypeScopeInnerClasses1
{
    // violation below 'Missing a Javadoc comment.'
    public class InnerPublic
    {
        // violation below 'Missing a Javadoc comment.'
        protected class InnerProtected
        {
            // violation below 'Missing a Javadoc comment.'
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
