////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * Outputs a JavaDoc tag as information. Can be used e.g. with the stylesheets
 * that sort the report by author name.
 * To define the format for a tag, set property tagFormat to a
 * regular expression.
 * This check uses two different severity levels. The normal one is used for
 * reporting when the tag is missing. The additional one (tagSeverity) is used
 * for the level of reporting when the tag exists. The default value for
 * tagSeverity is info.
 * </p>
 * <p> An example of how to configure the check for printing author name is:
 *</p>
 * <pre>
 * &lt;module name="WriteTag"&gt;
 *    &lt;property name="tag" value="@author"/&gt;
 *    &lt;property name="tagFormat" value="\S"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p> An example of how to configure the check to print warnings if an
 * "@incomplete" tag is found, and not print anything if it is not found:
 *</p>
 * <pre>
 * &lt;module name="WriteTag"&gt;
 *    &lt;property name="tag" value="@incomplete"/&gt;
 *    &lt;property name="tagFormat" value="\S"/&gt;
 *    &lt;property name="severity" value="ignore"/&gt;
 *    &lt;property name="tagSeverity" value="warning"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Daniel Grenner
 */
public class WriteTagCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISSING_TAG = "type.missingTag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WRITE_TAG = "javadoc.writeTag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_TAG_FORMAT = "type.tagFormat";

    /** Compiled regexp to match tag. **/
    private Pattern tagRegExp;
    /** Compiled regexp to match tag content. **/
    private Pattern tagFormatRegExp;

    /** Regexp to match tag. */
    private String tag;
    /** Regexp to match tag content. */
    private String tagFormat;
    /** The severity level of found tag reports. */
    private SeverityLevel tagSeverityLevel = SeverityLevel.INFO;

    /**
     * Sets the tag to check.
     * @param tag tag to check
     */
    public void setTag(String tag) {
        this.tag = tag;
        tagRegExp = CommonUtils.createPattern(tag + "\\s*(.*$)");
    }

    /**
     * Set the tag format.
     * @param format a {@code String} value
     */
    public void setTagFormat(String format) {
        tagFormat = format;
        tagFormatRegExp = CommonUtils.createPattern(format);
    }

    /**
     * Sets the tag severity level.  The string should be one of the names
     * defined in the {@code SeverityLevel} class.
     *
     * @param severity  The new severity level
     * @see SeverityLevel
     */
    public final void setTagSeverity(String severity) {
        tagSeverityLevel = SeverityLevel.getInstance(severity);
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.INTERFACE_DEF,
                          TokenTypes.CLASS_DEF,
                          TokenTypes.ENUM_DEF,
                          TokenTypes.ANNOTATION_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.INTERFACE_DEF,
                          TokenTypes.CLASS_DEF,
                          TokenTypes.ENUM_DEF,
                          TokenTypes.ANNOTATION_DEF,
                          TokenTypes.METHOD_DEF,
                          TokenTypes.CTOR_DEF,
                          TokenTypes.ENUM_CONSTANT_DEF,
                          TokenTypes.ANNOTATION_FIELD_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final FileContents contents = getFileContents();
        final int lineNo = ast.getLineNo();
        final TextBlock cmt =
            contents.getJavadocBefore(lineNo);
        if (cmt == null) {
            log(lineNo, MSG_MISSING_TAG, tag);
        }
        else {
            checkTag(lineNo, cmt.getText());
        }
    }

    /**
     * Verifies that a type definition has a required tag.
     * @param lineNo the line number for the type definition.
     * @param comment the Javadoc comment for the type definition.
     */
    private void checkTag(int lineNo, String... comment) {
        if (tagRegExp != null) {
            int tagCount = 0;
            for (int i = 0; i < comment.length; i++) {
                final String commentValue = comment[i];
                final Matcher matcher = tagRegExp.matcher(commentValue);
                if (matcher.find()) {
                    tagCount += 1;
                    final int contentStart = matcher.start(1);
                    final String content = commentValue.substring(contentStart);
                    if (tagFormatRegExp == null || tagFormatRegExp.matcher(content).find()) {
                        logTag(lineNo + i - comment.length, tag, content);
                    }
                    else {
                        log(lineNo + i - comment.length, MSG_TAG_FORMAT, tag, tagFormat);
                    }
                }
            }
            if (tagCount == 0) {
                log(lineNo, MSG_MISSING_TAG, tag);
            }
        }
    }

    /**
     * Log a message.
     *
     * @param line the line number where the error was found
     * @param tagName the javadoc tag to be logged
     * @param tagValue the contents of the tag
     *
     * @see java.text.MessageFormat
     */
    protected final void logTag(int line, String tagName, String tagValue) {
        final String originalSeverity = getSeverity();
        setSeverity(tagSeverityLevel.getName());

        log(line, MSG_WRITE_TAG, tagName, tagValue);

        setSeverity(originalSeverity);
    }
}
