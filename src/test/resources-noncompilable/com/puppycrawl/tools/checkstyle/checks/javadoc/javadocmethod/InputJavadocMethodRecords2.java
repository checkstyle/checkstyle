/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = true
allowMissingReturnTag = (default)false
tokens = METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;


public class InputJavadocMethodRecords2
{
    /**
     * @param subject       a value that uniquely identifies
     * @param firstName     the user's first name
     * @param nickName      the user's nickname
     * @param fullName      the user's full name
     * @param email         the user's email address
     * @param emailVerified {@code true} if the email address has been verified
     */
    public record Inner(String subject, String firstName,
                        String nickName, String fullName,
                        String email, boolean emailVerified)
    {
        /**
         * Creates a new UserInfo instance.
         *
         * @param subject       a value that uniquely identifies
         * @param firstName     the user's first name
         * @param nickName      the user's nickname
         * @param lastName      the user's full name
         * @param emailVerified {@code true} if the email address has been verified
         * @throws IllegalArgumentException if any of the arguments is empty
         */
        // violation 4 lines above 'Unused @param tag for 'lastName''
        public Inner
        {
        }
    }

    public record Inner2()
    {
        /**
         * Creates a new UserInfo instance.
         *
         * @param lastName      the user's full name
         */
        // violation 2 lines above 'Unused @param tag for 'lastName''
        public Inner2
        {
        }
    }

}
