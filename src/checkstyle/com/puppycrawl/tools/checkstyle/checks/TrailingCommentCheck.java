////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2004  Oliver Burn
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
 * The check.
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
        // do nothing
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
            // I don't want handle several comments on one line :(
            // Perhaps I'm wrong :)
            if (cppComments.containsKey(lineNo)
                && cComments.containsKey(lineNo)
                || cComments.containsKey(lineNo)
                && ((List) cComments.get(lineNo)).size() > 1)
            {
                log(lineNo.intValue(), "Too many comments.");
                continue;
            }

            final String line = getLines()[lineNo.intValue() - 1];
            String lineBefore = "";
            String lineAfter = "";
            if (cppComments.containsKey(lineNo)) {
                final TextBlock comment = (TextBlock) cppComments.get(lineNo);
                lineBefore = line.substring(0, comment.getStartColNo());
            }
            else if (cComments.containsKey(lineNo)) {
                final List commentList = (List) cComments.get(lineNo);
                final TextBlock comment =
                    (TextBlock) commentList.iterator().next();
                lineBefore = line.substring(0, comment.getStartColNo());
                if (comment.getText().length == 1) {
                    lineAfter = line.substring(comment.getEndColNo() + 1);
                }
            }
            lineAfter = lineAfter.trim();
            if (!blankLinePattern.match(lineBefore) || !"".equals(lineAfter)) {
                log(lineNo.intValue(), "trailing.comments");
            }
        }
    }
}
