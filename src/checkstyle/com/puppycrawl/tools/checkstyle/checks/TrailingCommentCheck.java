////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TextBlock;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.ConversionException;
import org.apache.regexp.RE;

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
    /**
     * Creates new instance of the check.
     * @throws ConversionException unable to parse DEFAULT_FORMAT.
     */
    public TrailingCommentCheck() throws ConversionException
    {
        super(DEFAULT_FORMAT);
    }

    /** {@inheritDoc} */
    public int[] getDefaultTokens()
    {
        return new int[0];
    }

    /** {@inheritDoc} */
    public void visitToken(DetailAST aAST)
    {
        throw new IllegalStateException("visitToken() shouldn't be called.");
    }

    /** {@inheritDoc} */
    public void beginTree(DetailAST aRootAST)
    {
        final RE blankLinePattern = getRegexp();
        final Map cppComments = getFileContents().getCppComments();
        final Map cComments = getFileContents().getCComments();
        final Set lines = new HashSet();
        lines.addAll(cppComments.keySet());
        lines.addAll(cComments.keySet());

        final Iterator linesIter = lines.iterator();
        while (linesIter.hasNext()) {
            final Integer lineNo = (Integer) linesIter.next();

            final String line = getLines()[lineNo.intValue() - 1];
            String lineBefore = "";
            if (cppComments.containsKey(lineNo)) {
                final TextBlock comment = (TextBlock) cppComments.get(lineNo);
                lineBefore = line.substring(0, comment.getStartColNo());
            }
            else if (cComments.containsKey(lineNo)) {
                final List commentList = (List) cComments.get(lineNo);
                final TextBlock comment =
                    (TextBlock) commentList.get(commentList.size() - 1);
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
            if (!blankLinePattern.match(lineBefore)) {
                log(lineNo.intValue(), "trailing.comments");
            }
        }
    }
}
