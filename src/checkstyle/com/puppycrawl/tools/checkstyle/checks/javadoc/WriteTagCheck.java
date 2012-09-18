////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.beanutils.ConversionException;

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
 * @version 1.0
 */
public class WriteTagCheck
    extends Check
{
    /** compiled regexp to match tag **/
    private Pattern mTagRE;
    /** compiled regexp to match tag content **/
    private Pattern mTagFormatRE;

    /** regexp to match tag */
    private String mTag;
    /** regexp to match tag content */
    private String mTagFormat;
    /** the severity level of found tag reports */
    private SeverityLevel mTagSeverityLevel = SeverityLevel.INFO;

    /**
     * Sets the tag to check.
     * @param aTag tag to check
     * @throws ConversionException If the tag is not a valid regular exception.
     */
    public void setTag(String aTag)
        throws ConversionException
    {
        try {
            mTag = aTag;
            mTagRE = Utils.getPattern(aTag + "\\s*(.*$)");
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + aTag, e);
        }
    }

    /**
     * Set the tag format.
     * @param aFormat a <code>String</code> value
     * @throws ConversionException unable to parse aFormat
     */
    public void setTagFormat(String aFormat)
        throws ConversionException
    {
        try {
            mTagFormat = aFormat;
            mTagFormatRE = Utils.getPattern(aFormat);
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }
    }

    /**
     * Sets the tag severity level.  The string should be one of the names
     * defined in the <code>SeverityLevel</code> class.
     *
     * @param aSeverity  The new severity level
     * @see SeverityLevel
     */
    public final void setTagSeverity(String aSeverity)
    {
        mTagSeverityLevel = SeverityLevel.getInstance(aSeverity);
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.INTERFACE_DEF,
                          TokenTypes.CLASS_DEF,
                          TokenTypes.ENUM_DEF,
                          TokenTypes.ANNOTATION_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens()
    {
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
    public void visitToken(DetailAST aAST)
    {
        final FileContents contents = getFileContents();
        final int lineNo = aAST.getLineNo();
        final TextBlock cmt =
            contents.getJavadocBefore(lineNo);
        if (cmt == null) {
            log(lineNo, "type.missingTag", mTag);
        }
        else {
            checkTag(lineNo, cmt.getText(), mTag, mTagRE, mTagFormatRE,
                mTagFormat);
        }
    }

    /**
     * Verifies that a type definition has a required tag.
     * @param aLineNo the line number for the type definition.
     * @param aComment the Javadoc comment for the type definition.
     * @param aTag the required tag name.
     * @param aTagRE regexp for the full tag.
     * @param aFormatRE regexp for the tag value.
     * @param aFormat pattern for the tag value.
     */
    private void checkTag(
            int aLineNo,
            String[] aComment,
            String aTag,
            Pattern aTagRE,
            Pattern aFormatRE,
            String aFormat)
    {
        if (aTagRE == null) {
            return;
        }

        int tagCount = 0;
        for (int i = 0; i < aComment.length; i++) {
            final String s = aComment[i];
            final Matcher matcher = aTagRE.matcher(s);
            if (matcher.find()) {
                tagCount += 1;
                final int contentStart = matcher.start(1);
                final String content = s.substring(contentStart);
                if ((aFormatRE != null) && !aFormatRE.matcher(content).find()) {
                    log(aLineNo + i - aComment.length, "type.tagFormat", aTag,
                        aFormat);
                }
                else {
                    logTag(aLineNo + i - aComment.length, aTag, content);
                }

            }
        }
        if (tagCount == 0) {
            log(aLineNo, "type.missingTag", aTag);
        }

    }


    /**
     * Log a message.
     *
     * @param aLine the line number where the error was found
     * @param aTag the javdoc tag to be logged
     * @param aTagValue the contents of the tag
     *
     * @see java.text.MessageFormat
     */
    protected final void logTag(int aLine, String aTag, String aTagValue)
    {
        final String originalSeverity = getSeverity();
        setSeverity(mTagSeverityLevel.getName());

        log(aLine, "javadoc.writeTag", aTag, aTagValue);

        setSeverity(originalSeverity);
    }
}
