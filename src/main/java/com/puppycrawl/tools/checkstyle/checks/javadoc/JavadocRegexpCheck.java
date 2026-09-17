///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks that Javadoc comments do not match a configured regular expression.
 * </div>
 *
 * <p>
 * The check can operate on raw Javadoc source or on the text visible to readers.
 * Raw matching includes Javadoc tags, HTML tags, HTML attributes, and formatting
 * characters. Visible-text matching ignores markup and matches only Javadoc text content.
 * </p>
 *
 * @since 13.10.0
 */
@FileStatefulCheck
public class JavadocRegexpCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_JAVADOC_REGEXP = "javadoc.regexp.match";

    /**
     * Text fragments visible to readers of the current Javadoc comment.
     */
    private final List<String> visibleText = new ArrayList<>();

    /**
     * Specify the regular expression to match forbidden Javadoc content.
     */
    @XdocsPropertyType(PropertyType.PATTERN)
    private String format = "^$";

    /**
     * Control whether to ignore case when matching.
     */
    private boolean ignoreCase;

    /**
     * Control whether to ignore Javadoc and HTML markup when matching.
     */
    private boolean ignoreMarkup;

    /**
     * Line number of the previous collected text node.
     */
    private int previousTextLineNumber;

    /**
     * Creates a new {@code JavadocRegexpCheck} instance.
     */
    public JavadocRegexpCheck() {
        // no code by default
    }

    /**
     * Setter to specify the regular expression to match forbidden Javadoc content.
     *
     * @param format regular expression to match forbidden Javadoc content.
     * @since 13.10.0
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Setter to control whether to ignore case when matching.
     *
     * @param ignoreCase whether to ignore case when matching.
     * @since 13.10.0
     */
    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    /**
     * Setter to control whether to ignore Javadoc and HTML markup when matching.
     *
     * @param ignoreMarkup whether to ignore Javadoc and HTML markup when matching.
     * @since 13.10.0
     */
    public void setIgnoreMarkup(boolean ignoreMarkup) {
        this.ignoreMarkup = ignoreMarkup;
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.TEXT,
        };
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public void beginJavadocTree(DetailNode rootAst) {
        visibleText.clear();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (!isIgnoredText(ast)) {
            appendVisibleText(ast);
        }
    }

    @Override
    public void finishJavadocTree(DetailNode rootAst) {
        final String content;
        if (ignoreMarkup) {
            content = String.join("", visibleText);
        }
        else {
            content = JavadocUtil.getJavadocCommentContent(getBlockCommentAst());
        }

        final Pattern regexp = createRegexp();
        if (regexp.matcher(content).find()) {
            log(rootAst.getLineNumber(), MSG_JAVADOC_REGEXP, regexp.pattern());
        }
    }

    /**
     * Creates the regexp based on {@link #format} and {@link #ignoreCase}.
     *
     * @return pattern used to find forbidden content.
     */
    private Pattern createRegexp() {
        final int compileFlags;
        if (ignoreCase) {
            compileFlags = Pattern.CASE_INSENSITIVE;
        }
        else {
            compileFlags = 0;
        }
        return CommonUtil.createPattern(format, compileFlags);
    }

    /**
     * Appends visible text from a Javadoc text node.
     *
     * @param textNode text node to append.
     */
    private void appendVisibleText(DetailNode textNode) {
        if (!visibleText.isEmpty() && previousTextLineNumber < textNode.getLineNumber()) {
            visibleText.add(" ");
        }
        visibleText.add(textNode.getText());
        previousTextLineNumber = textNode.getLineNumber();
    }

    /**
     * Checks whether a text node belongs to a non-rendered HTML comment.
     *
     * @param textNode text node to check.
     * @return {@code true} if the text node should be ignored.
     */
    private static boolean isIgnoredText(DetailNode textNode) {
        boolean result = false;
        DetailNode current = textNode;

        while (current != null) {
            if (current.getType() == JavadocCommentsTokenTypes.HTML_COMMENT) {
                result = true;
                break;
            }
            current = current.getParent();
        }

        return result;
    }

}
