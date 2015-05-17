////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc;

/**
 * Used to keep track of a tag and the text that follows it.
 *
 * @author Chris Stillwell
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

    /** if this tag is self-closed. */
    private final boolean closedTag;

    /** if the tag is incomplete. */
    private final boolean incomplete;

    /**
     * Construct the HtmlTag.
     * @param id the HTML tag name.
     * @param lineNo the source line number of this tag.
     * @param position the position within the text of this tag.
     * @param closedTag if this tag is self-closed (XHTML style)
     * @param incomplete is the tag is incomplete.
     * @param text the line of comment text for this tag.
     */
    HtmlTag(String id, int lineNo, int position, boolean closedTag,
            boolean incomplete, String text) {
        this.id = !"".equals(id) && id.charAt(0) == '/'
            ? id.substring(1) : id;
        this.lineNo = lineNo;
        this.position = position;
        this.text = text;
        this.closedTag = closedTag;
        this.incomplete = incomplete;
    }

    /**
     * Returns the id (name) of this tag.
     * @return a String id.
     */
    public String getId() {
        return id;
    }

    /**
     * Indicates if this tag is a close (end) tag.
     * @return <code>true</code> is this is a close tag.
     */
    public boolean isCloseTag() {
        if (position == text.length() - 1) {
            return false;
        }
        return text.charAt(position + 1) == '/';
    }

    /**
     * Indicates if this tag is a self-closed XHTML style.
     * @return <code>true</code> is this is a self-closed tag.
     */
    public boolean isClosedTag() {
        return closedTag;
    }

    /**
     * Indicates if this tag is incomplete (has no close &gt;).
     * @return <code>true</code> if the tag is incomplete.
     */
    public boolean isIncompleteTag() {
        return incomplete;
    }

    /**
     * Returns the source line number where this tag was found.
     * Used for displaying a Checkstyle error.
     * @return an int line number.
     */
    public int getLineNo() {
        return lineNo;
    }

    /**
     * Returns the position with in the comment line where this tag
     * was found.  Used for displaying a Checkstyle error.
     * @return an int relative to zero.
     */
    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        final int startOfText = position;
        final int endOfText =
            Math.min(startOfText + HtmlTag.MAX_TEXT_LEN, text.length());
        return text.substring(startOfText, endOfText);
    }
}
