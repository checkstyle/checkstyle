package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

/**
 * Some Javadoc.
 * @since 8.36 //warn
 */
class InputIncorrectRequireEmptyLineBeforeBlockTagGroupCheck {

    /**
     * This documents the private method.
     * @param thisParamTagNeedsNewline this documents the parameter. //warn
     */
    private boolean paramTagNeedsNewline(boolean thisParamTagNeedsNewline) {
        return false;
    }

    /**
     * This documents the private method.
     * @param thisParamTagNeedsNewline this documents the parameter. //warn
     * @return this one does not need an empty line, but the tag before this one does.
     */
    private boolean paramMultiTagNeedsNewline(boolean thisParamTagNeedsNewline) {
        return false;
    }
}
