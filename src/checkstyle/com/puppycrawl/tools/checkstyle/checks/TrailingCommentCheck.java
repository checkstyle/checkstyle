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
package com.puppycrawl.tools.checkstyle.checks;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.Utils;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.beanutils.ConversionException;

/**
 * <p>
 * The check to ensure that requires that comments be the only thing on a line.
 * For the case of // comments that means that the only thing that should
 * precede it is whitespace.
 * It doesn't check comments if they do not end line, i.e. it accept
 * the following:
 * <code>Thread.sleep( 10 &lt;some comment here&gt; );</code>
 * Format property is intended to deal with the "} // while" example.
 * </p>
 * <p>
 * Rationale: Steve McConnel in &quot;Code Complete&quot; suggests that endline
 * comments are a bad practice. An end line comment would
 * be one that is on the same line as actual code. For example:
 * <pre>
 *  a = b + c;      // Some insightful comment
 *  d = e / f;        // Another comment for this line
 * </pre>
 * Quoting &quot;Code Complete&quot; for the justfication:
 * <ul>
 * <li>
 * &quot;The comments have to be aligned so that they do not
 * interfere with the visual structure of the code. If you don't
 * align them neatly, they'll make your listing look like it's been
 * through a washing machine.&quot;
 * </li>
 * <li>
 * &quot;Endline comments tend to be hard to format...It takes time
 * to align them. Such time is not spent learning more about
 * the code; it's dedicated solely to the tedious task of
 * pressing the spacebar or tab key.&quot;
 * </li>
 * <li>
 * &quot;Endline comments are also hard to maintain. If the code on
 * any line containing an endline comment grows, it bumps the
 * comment farther out, and all the other endline comments will
 * have to bumped out to match. Styles that are hard to
 * maintain aren't maintained....&quot;
 * </li>
 * <li>
 * &quot;Endline comments also tend to be cryptic. The right side of
 * the line doesn't offer much room and the desire to keep the
 * comment on one line means the comment must be short.
 * Work then goes into making the line as short as possible
 * instead of as clear as possible. The comment usually ends
 * up as cryptic as possible....&quot;
 * </li>
 * <li>
 * &quot;A systemic problem with endline comments is that it's hard
 * to write a meaningful comment for one line of code. Most
 * endline comments just repeat the line of code, which hurts
 * more than it helps.&quot;
 * </li>
 * </ul>
 * His comments on being hard to maintain when the size of
 * the line changes are even more important in the age of
 * automated refactorings.
 * </p>
 * <p>
 * To configure the check so it enforces only comment on a line:
 * <pre>
 * &lt;module name=&quot;TrailingComment&quot;&gt;
 *    &lt;property name=&quot;format&quot; value=&quot;^\\s*$&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * </p>
 * @author o_sukhodolsky
 */
public class TrailingCommentCheck extends AbstractFormatCheck
{
    /** default format for allowed blank line. */
    private static final String DEFAULT_FORMAT = "^[\\s\\}\\);]*$";

    /** pattern for legal trailing comment. */
    private Pattern mLegalComment;

    /**
     * Sets patter for legal trailing comments.
     * @param aFormat format to set.
     * @throws ConversionException unable to parse a given format.
     */
    public void setLegalComment(final String aFormat)
        throws ConversionException
    {
        try {
            mLegalComment = Utils.getPattern(aFormat);
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }
    }
    /**
     * Creates new instance of the check.
     * @throws ConversionException unable to parse DEFAULT_FORMAT.
     */
    public TrailingCommentCheck() throws ConversionException
    {
        super(DEFAULT_FORMAT);
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[0];
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        throw new IllegalStateException("visitToken() shouldn't be called.");
    }

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        final Pattern blankLinePattern = getRegexp();
        final Map<Integer, TextBlock> cppComments = getFileContents()
                .getCppComments();
        final Map<Integer, List<TextBlock>> cComments = getFileContents()
                .getCComments();
        final Set<Integer> lines = Sets.newHashSet();
        lines.addAll(cppComments.keySet());
        lines.addAll(cComments.keySet());

        for (Integer lineNo : lines) {
            final String line = getLines()[lineNo.intValue() - 1];
            String lineBefore = "";
            TextBlock comment = null;
            if (cppComments.containsKey(lineNo)) {
                comment = cppComments.get(lineNo);
                lineBefore = line.substring(0, comment.getStartColNo());
            }
            else if (cComments.containsKey(lineNo)) {
                final List<TextBlock> commentList = cComments.get(lineNo);
                comment = commentList.get(commentList.size() - 1);
                lineBefore = line.substring(0, comment.getStartColNo());
                if (comment.getText().length == 1) {
                    final String lineAfter =
                        line.substring(comment.getEndColNo() + 1).trim();
                    if (!"".equals(lineAfter)) {
                        // do not check comment which doesn't end line
                        continue;
                    }
                }
            }
            if ((comment != null)
                && !blankLinePattern.matcher(lineBefore).find()
                && !isLegalComment(comment))
            {
                log(lineNo.intValue(), "trailing.comments");
            }
        }
    }

    /**
     * Checks if given comment is legal (single-line and matches to the
     * pattern).
     * @param aComment comment to check.
     * @return true if the comment if legal.
     */
    private boolean isLegalComment(final TextBlock aComment)
    {
        if (mLegalComment == null) {
            return false;
        }
        // multi-line comment can not be legal
        if (aComment.getStartLineNo() != aComment.getEndLineNo()) {
            return false;
        }
        String commentText = aComment.getText()[0];
        // remove chars which start comment
        commentText = commentText.substring(2);
        // if this is a C-style comment we need to remove its end
        if (commentText.endsWith("*/")) {
            commentText = commentText.substring(0, commentText.length() - 2);
        }
        commentText = commentText.trim();
        return mLegalComment.matcher(commentText).find();
    }
}
