package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

/**
 * Some Javadoc.
 * @since 8.33 //warn
 */
class InputIncorrectRequireEmptyLineBeforeAtClauseBlockCheck {

    /**
     * This documents the private method.
     * @param thisParamAtClauseNeedsNewline this documents the parameter. //warn
     */
    private boolean paramAtClauseNeedsNewline(boolean thisParamAtClauseNeedsNewline) {
        return false;
    }

    /**
     * This documents the private method.
     * @param thisParamAtClauseNeedsNewline this documents the parameter. //warn
     * @return this one does not need an empty line, but the at-clause before this one does.
     */
    private boolean paramMultiAtClauseNeedsNewline(boolean thisParamAtClauseNeedsNewline) {
        return false;
    }
}
