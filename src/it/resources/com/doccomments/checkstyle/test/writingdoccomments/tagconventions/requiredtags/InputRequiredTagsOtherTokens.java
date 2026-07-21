package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.requiredtags;

/**
 * Input for JavadocMethodCheck required tags examples for additional tokens.
 */
public class InputRequiredTagsOtherTokens {

    public InputRequiredTagsOtherTokens() {
    }

    /**
     * Valid compact constructor record.
     *
     * @param name name to store
     */
    record ValidCompactCtor(String name) {

        /**
         * Creates a record instance.
         *
         * @param name name to store
         */
        ValidCompactCtor {
        }
    }

    /**
     * Invalid compact constructor record.
     *
     * @param name name to store
     */
    record InvalidCompactCtor(String name) {

        // violation 4 lines below 'Expected @param tag for 'name'.'
        /**
         * Creates a record instance.
         */
        InvalidCompactCtor {
        }
    }

    /**
     * Required tags annotation.
     */
    @interface RequiredTagsAnnotation {

        /**
         * Returns configured value.
         *
         * @return configured value
         */
        String value();

        // violation 4 lines below '@return tag should be present and have description.'
        /**
         * Returns missing value.
         */
        String missing();
    }
}
