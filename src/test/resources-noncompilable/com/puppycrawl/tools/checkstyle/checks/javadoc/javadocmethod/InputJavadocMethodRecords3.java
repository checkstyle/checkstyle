/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;


public class InputJavadocMethodRecords3
{
    /**
     *
     * @param email         the user's email address
     * @param emailVerified {@code true} if the email address has been verified
     */
    @Override
    public record Inner(String subject, String firstName,
                        String nickName, String fullName,
                        String email, boolean emailVerified)
    {
        /**
         * Creates a new UserInfo instance.
         *
         */
        @Override
        public Inner
        {
        }
    }
}
