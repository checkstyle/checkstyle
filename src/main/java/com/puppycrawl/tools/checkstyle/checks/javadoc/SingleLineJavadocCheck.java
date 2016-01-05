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

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtils;

/**
 * Checks that a JavaDoc block can fit on a single line and doesn't
 * contain at-clauses. Javadoc comment that contains at least one at-clause
 * should be formatted in a few lines.<br>
 * All inline at-clauses are ignored by default.
 *
 * <p>The Check has the following properties:
 * <br><b>ignoredTags</b> - allows to specify a list of at-clauses
 * (including custom at-clauses) to be ignored by the check.
 * <br><b>ignoreInlineTags</b> - whether inline at-clauses must be ignored.
 * </p>
 *
 * <p>Default configuration:
 * <pre>
 * &lt;module name=&quot;SingleLineJavadoc&quot;/&gt;
 * </pre>
 * To specify a list of ignored at-clauses and make inline at-clauses not ignored in general:
 * <pre>
 * &lt;module name=&quot;SingleLineJavadoc&quot;&gt;
 *     &lt;property name=&quot;ignoredTags&quot; value=&quot;&#64;inheritDoc, &#64;link&quot;/&gt;
 *     &lt;property name=&quot;ignoreInlineTags&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author baratali
 * @author maxvetrenko
 * @author vladlis
 *
 */
public class SingleLineJavadocCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "singleline.javadoc";

    /**
     * Allows to specify a list of tags to be ignored by check.
     */
    private List<String> ignoredTags = new ArrayList<>();

    /** Whether inline tags must be ignored. **/
    private boolean ignoreInlineTags = true;

    /**
     * Sets a list of tags to be ignored by check.
     *
     * @param tags to be ignored by check.
     */
    public void setIgnoredTags(String tags) {
        ignoredTags =
            Lists.newArrayList(Splitter.on(",").omitEmptyStrings().trimResults().split(tags));
    }

    /**
     * Sets whether inline tags must be ignored.
     *
     * @param ignoreInlineTags whether inline tags must be ignored.
     */
    public void setIgnoreInlineTags(boolean ignoreInlineTags) {
        this.ignoreInlineTags = ignoreInlineTags;
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocTokenTypes.JAVADOC,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.BLOCK_COMMENT_BEGIN };
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (isSingleLineJavadoc(getBlockCommentAst())
                && (hasJavadocTags(ast) || !ignoreInlineTags && hasJavadocInlineTags(ast))) {
            log(ast.getLineNumber(), MSG_KEY);
        }
    }

    /**
     * Checks if comment is single line comment.
     *
     * @param blockCommentStart the AST tree in which a block comment starts
     * @return true, if comment is single line comment.
     */
    private static boolean isSingleLineJavadoc(DetailAST blockCommentStart) {
        final DetailAST blockCommentEnd = blockCommentStart.getLastChild();
        return blockCommentStart.getLineNo() == blockCommentEnd.getLineNo();
    }

    /**
     * Checks if comment has javadoc tags which are not ignored. Also works
     * on custom tags. As block tags can be interpreted only at the beginning of a line,
     * only the first instance is checked.
     *
     * @param javadocRoot javadoc root node.
     * @return true, if comment has javadoc tags which are not ignored.
     * @see <a href=
     * http://docs.oracle.com/javase/7/docs/technotes/tools/windows/javadoc.html#blockandinlinetags>
     * Block and inline tags</a>
     */
    private boolean hasJavadocTags(DetailNode javadocRoot) {
        final DetailNode javadocTagSection =
                JavadocUtils.findFirstToken(javadocRoot, JavadocTokenTypes.JAVADOC_TAG);
        return javadocTagSection != null && !isTagIgnored(javadocTagSection);
    }

    /**
     * Checks if comment has in-line tags which are not ignored.
     *
     * @param javadocRoot javadoc root node.
     * @return true, if comment has in-line tags which are not ignored.
     * @see <a href=
     * http://docs.oracle.com/javase/7/docs/technotes/tools/windows/javadoc.html#javadoctags>
     * JavadocTags</a>
     */
    private boolean hasJavadocInlineTags(DetailNode javadocRoot) {
        DetailNode javadocTagSection =
                JavadocUtils.findFirstToken(javadocRoot, JavadocTokenTypes.JAVADOC_INLINE_TAG);
        boolean foundTag = false;
        while (javadocTagSection != null) {
            if (!isTagIgnored(javadocTagSection)) {
                foundTag = true;
                break;
            }
            javadocTagSection = JavadocUtils.getNextSibling(
                    javadocTagSection, JavadocTokenTypes.JAVADOC_INLINE_TAG);
        }
        return foundTag;
    }

    /**
     * Checks if list of ignored tags contains javadocTagSection's javadoc tag.
     *
     * @param javadocTagSection to check javadoc tag in.
     * @return true, if ignoredTags contains javadocTagSection's javadoc tag.
     */
    private boolean isTagIgnored(DetailNode javadocTagSection) {
        return ignoredTags.contains(JavadocUtils.getTagName(javadocTagSection));
    }
}
