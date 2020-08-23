package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

/**
 * Some Javadoc.
 *
 * @since 8.36
 */
class InputCorrectRequireEmptyLineBeforeBlockTagGroupCheck {

    /**
     * This javadoc does not have a tag. There should be no violations.
     */
    public static final byte NO_TAG = 0;

    /**
     * This javadoc does has one tag, with an empty line. There should be no violations.
     *
     * @since 8.36
     */
    public static final byte ONE_TAG = 0;

    /**
     * This javadoc has multiple tag, with an empty line before the. There should be no
     * violations.
     *
     * @param input this is the first tag.
     * @return this is the second tag.
     */
    public static boolean test(boolean input) {
        return false;
    }

    /**
     * @return this only has an tag.
     */
    public static boolean test() {
        return false;
    }
}
