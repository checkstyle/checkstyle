package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

/**
 * Some Javadoc.
 * @since 8.36 // violation 'Javadoc tag '@since' should be preceded with an empty line.'
 */
class InputIncorrectRequireEmptyLineBeforeBlockTagGroupCheck {

    /**
     * This documents the private method.
     * @param thisParamTagNeedsNewline this documents the parameter.
     * // violation above 'Javadoc tag '@param' should be preceded with an empty line.'
     */
    private boolean paramTagNeedsNewline(boolean thisParamTagNeedsNewline) {
        return false;
    }

    /**
     * This documents the private method.
     * @param thisParamTagNeedsNewline this documents the parameter.
     * // violation above 'Javadoc tag '@param' should be preceded with an empty line.'
     * @return this one does not need an empty line, but the tag before this one does.
     */
    private boolean paramMultiTagNeedsNewline(boolean thisParamTagNeedsNewline) {
        return false;
    }
}
