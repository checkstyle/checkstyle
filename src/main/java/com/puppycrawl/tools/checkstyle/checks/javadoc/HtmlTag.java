////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
///

package com.puppycrawl.tools.checkstyle.checks.javadoc;

/**
 * Used to keep track of a tag and the text that follows it.
 *
 */
class HtmlTag {

    /** The maximum length of text to display with this tag. */
    private static final int MAX_TEXT_LEN = 60;

    /** The HTML tag name. */
    private final String id;

    /** The line number in the source file where this tag was found. */
    private final int lineNo;

    /** The position within the line where this tag was found. */
    private final int position;

    /** The comment line of text where this tag appears. */
    private final String text;

    /** If this tag is self-closed. */
    private final boolean closedTag;

    /** If the tag is incomplete. */
    private final boolean incompleteTag;

    /**
     * Construct the HtmlTag.
     *
     * @param id the HTML tag name.
     * @param lineNo the source line number of this tag.
     * @param position the position within the text of this tag.
     * @param closedTag if this tag is self-closed (XHTML style)
     * @param incomplete is the tag is incomplete.
     * @param text the line of comment text for this tag.
     */
    /* package */ HtmlTag(String id, int lineNo, int position, boolean closedTag,
                          boolean incomplete, String text) {
        this.id = id;
        this.lineNo = lineNo;
        this.position = position;
        this.text = text;
        this.closedTag = closedTag;
        incompleteTag = incomplete;
    }

    /**
     * Returns the id (name) of this tag.
     *
     * @return a String id.
     */
    public String getId() {
        return id;
    }

    /**
     * Indicates if this tag is a close (end) tag.
     *
     * @return {@code true} is this is a close tag.
     */
    public boolean isCloseTag() {
        return position != text.length() - 1 && text.charAt(position + 1) == '/';
    }

    /**
     * Indicates if this tag is a self-closed XHTML style.
     *
     * @return {@code true} is this is a self-closed tag.
     */
    public boolean isClosedTag() {
        return closedTag;
    }

    /**
     * Indicates if this tag is incomplete (has no close &gt;).
     *
     * @return {@code true} if the tag is incomplete.
     */
    public boolean isIncompleteTag() {
        return incompleteTag;
    }

    /**
     * Returns the source line number where this tag was found.
     * Used for displaying a Checkstyle violation.
     *
     * @return an int line number.
     */
    public int getLineNo() {
        return lineNo;
    }

    /**
     * Returns the position with in the comment line where this tag
     * was found.  Used for displaying a Checkstyle violation.
     *
     * @return an int relative to zero.
     */
    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "HtmlTag[id='" + id + '\''
            + ", lineNo=" + lineNo
            + ", position=" + position
            + ", text='" + text + '\''
            + ", closedTag=" + closedTag
            + ", incompleteTag=" + incompleteTag + ']';
    }

    /**
     * Returns the comment line of text where this tag appears.
     *
     * @return text of the tag
     */
    public String getText() {
        final int startOfText = position;
        final int endOfText = Math.min(startOfText + MAX_TEXT_LEN, text.length());
        return text.substring(startOfText, endOfText);
    }

}
