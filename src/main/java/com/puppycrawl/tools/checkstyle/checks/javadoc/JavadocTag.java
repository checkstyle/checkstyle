///
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
 * Represents a Javadoc tag. Provides methods to query what type of tag it is.
 */
public class JavadocTag {

    /** The line number of the tag. **/
    private final int lineNo;
    /** The column number of the tag. **/
    private final int columnNo;
    /** An optional first argument. For example the parameter name. **/
    private final String firstArg;
    /** The JavadocTagInfo representing this tag. **/
    private final JavadocTagInfo tagInfo;

    /**
     * Constructs the object.
     *
     * @param line the line number of the tag
     * @param column the column number of the tag
     * @param tag the tag string
     * @param firstArg the tag argument
     **/
    public JavadocTag(int line, int column, String tag, String firstArg) {
        lineNo = line;
        columnNo = column;
        this.firstArg = firstArg;
        tagInfo = JavadocTagInfo.fromName(tag);
    }

    /**
     * Constructs the object.
     *
     * @param line the line number of the tag
     * @param column the column number of the tag
     * @param tag the tag string
     **/
    public JavadocTag(int line, int column, String tag) {
        this(line, column, tag, null);
    }

    /**
     * Gets tag name.
     *
     * @return the tag string
     */
    public String getTagName() {
        return tagInfo.getName();
    }

    /**
     * Returns first argument.
     *
     * @return the first argument. null if not set.
     */
    public String getFirstArg() {
        return firstArg;
    }

    /**
     * Gets the line number.
     *
     * @return the line number
     */
    public int getLineNo() {
        return lineNo;
    }

    /**
     * Gets column number.
     *
     * @return the column number
     */
    public int getColumnNo() {
        return columnNo;
    }

    @Override
    public String toString() {
        return "JavadocTag[tag='" + tagInfo.getName()
                + "' lineNo=" + lineNo
                + ", columnNo=" + columnNo
                + ", firstArg='" + firstArg + "']";
    }

    /**
     * Checks that the tag is an 'return' tag.
     *
     * @return whether the tag is an 'return' tag
     */
    public boolean isReturnTag() {
        return tagInfo == JavadocTagInfo.RETURN;
    }

    /**
     * Checks that the tag is an 'param' tag.
     *
     * @return whether the tag is an 'param' tag
     */
    public boolean isParamTag() {
        return tagInfo == JavadocTagInfo.PARAM;
    }

    /**
     * Checks that the tag is an 'throws' or 'exception' tag.
     *
     * @return whether the tag is an 'throws' or 'exception' tag
     */
    public boolean isThrowsTag() {
        return tagInfo == JavadocTagInfo.THROWS
            || tagInfo == JavadocTagInfo.EXCEPTION;
    }

    /**
     * Checks that the tag is a 'see' or 'inheritDoc' tag.
     *
     * @return whether the tag is a 'see' or 'inheritDoc' tag
     */
    public boolean isSeeOrInheritDocTag() {
        return tagInfo == JavadocTagInfo.SEE || isInheritDocTag();
    }

    /**
     * Checks that the tag is a 'inheritDoc' tag.
     *
     * @return whether the tag is a 'inheritDoc' tag
     */
    public boolean isInheritDocTag() {
        return tagInfo == JavadocTagInfo.INHERIT_DOC;
    }

    /**
     * Checks that the tag can contain references to imported classes.
     *
     * @return whether the tag can contain references to imported classes
     */
    public boolean canReferenceImports() {
        return tagInfo == JavadocTagInfo.SEE
                || tagInfo == JavadocTagInfo.LINK
                || tagInfo == JavadocTagInfo.VALUE
                || tagInfo == JavadocTagInfo.LINKPLAIN
                || tagInfo == JavadocTagInfo.THROWS
                || tagInfo == JavadocTagInfo.EXCEPTION;
    }

    /**
     * Checks that the tag is a inline tag.
     *
     * @return whether the tag is a inline tag
     */
    public boolean isInlineTag() {
        return tagInfo.getType() == JavadocTagInfo.Type.INLINE;
    }

}
