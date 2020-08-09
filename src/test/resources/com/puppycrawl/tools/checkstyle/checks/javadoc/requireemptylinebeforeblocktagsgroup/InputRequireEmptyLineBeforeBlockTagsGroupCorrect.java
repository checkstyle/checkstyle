package com.puppycrawl.tools.checkstyle.checks.javadoc.requireemptylinebeforeblocktagsgroup;

/**
 * Config: default
 * Some Javadoc.
 *
 * @since 8.35 // ok
 */
class InputRequireEmptyLineBeforeBlockTagsGroupCorrect {

    /**
     * This javadoc does not have a tag. There should be no violations.
     */
    public static final byte NO_TAG = 0;

    /**
     * This javadoc does has one tag, with an empty line. There should be no violations.
     *
     * @since 8.0 // ok
     */
    public static final byte ONE_TAG = 0;

    /**
     * This javadoc has multiple tags, with an empty line before the. There should be no
     * violations.
     *
     * @param input this is the first tag. // ok
     * @return this is the second tag.
     */
    public static boolean test(boolean input) {
        return false;
    }

    /**
     * @return this only has an tag. // ok
     */
    public static boolean test() {
        return false;
    }

    /**
     *@return this tag has no whitespace before it. // ok
     */
    public static boolean noWhiteSpace() {
        return false;
    }
}
