////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * A check for TODO comments.
 * Actually it is a generic {@link java.util.regex.Pattern regular expression}
 * matcher on Java comments.
 * To check for other patterns in Java comments, set property format.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="TodoComment"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check for comments that contain
 * <code>WARNING</code> is:
 * </p>
 * <pre>
 * &lt;module name="TodoComment"&gt;
 *    &lt;property name="format" value="WARNING"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Oliver Burn
 * @version 1.0
 */
public class TodoCommentCheck
    extends AbstractFormatCheck
{
    /**
     * Creates a new <code>TodoCommentCheck</code> instance.
     */
    public TodoCommentCheck()
    {
        super("TODO:"); // the empty language
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[0];
    }

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        final FileContents contents = getFileContents();
        checkCppComments(contents);
        checkBadComments(contents);
    }

    /**
     * Checks the C++ comments for todo expressions.
     * @param aContents the <code>FileContents</code>
     */
    private void checkCppComments(FileContents aContents)
    {
        final Map<Integer, TextBlock> comments = aContents.getCppComments();
        for (Map.Entry<Integer, TextBlock> entry : comments.entrySet()) {
            final String cmt = entry.getValue().getText()[0];
            if (getRegexp().matcher(cmt).find()) {
                log(entry.getKey().intValue(), "todo.match", getFormat());
            }
        }
    }

    /**
     * Checks the C-style comments for todo expressions.
     * @param aContents the <code>FileContents</code>
     */
    private void checkBadComments(FileContents aContents)
    {
        final Map<Integer, List<TextBlock>> allComments = aContents
                .getCComments();
        for (Map.Entry<Integer, List<TextBlock>> entry : allComments.entrySet())
        {
            for (TextBlock line : entry.getValue()) {
                final String[] cmt = line.getText();
                for (int i = 0; i < cmt.length; i++) {
                    if (getRegexp().matcher(cmt[i]).find()) {
                        log(entry.getKey().intValue() + i, "todo.match",
                                getFormat());
                    }
                }
            }
        }
    }
}
