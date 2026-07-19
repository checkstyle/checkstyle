/*
RequireEmptyLineBeforeBlockTagGroup
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.requireemptylinebeforeblocktaggroup;

/**
 * Some Javadoc.
 *
 * @since 8.36
 */
class InputRequireEmptyLineBeforeBlockTagGroupCorrect2 {

    /**
     ** @return this only has a tag with multiple leading asterisks.
     */
    public static boolean onlyTagWithMultipleLeadingAsterisksAndSpace() {
        return false;
    }

    /**
     **@return this only has a tag with multiple leading asterisks.
     */
    public static boolean onlyTagWithMultipleLeadingAsterisksNoSpace() {
        return false;
    }

    /***
     * @return this only has a tag with an extra opening asterisk.
     */
    public static boolean onlyTagWithExtraOpeningAsterisk() {
        return false;
    }

    /*************************************************
     *** @return this only has a tag in a boxed comment.
     **********************************/
    public static boolean onlyTagInBoxedComment() {
        return false;
    }

    /**
     * This javadoc has text and an empty line before the tag.
     *
     ** @return this tag has multiple leading asterisks.
     */
    public static boolean textAndEmptyLineBeforeMultipleLeadingAsterisks() {
        return false;
    }
}
