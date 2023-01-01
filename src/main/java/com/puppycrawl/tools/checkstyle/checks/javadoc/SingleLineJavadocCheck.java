///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

import java.util.Set;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that a Javadoc block can fit in a single-line and doesn't contain block tags.
 * Javadoc comment that contains at least one block tag should be formatted in a few lines.
 * </p>
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations
 * if the Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">Tight-HTML Rules</a>.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code ignoredTags} - Specify
 * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html#CHDBEFIF">
 * block tags</a> which are ignored by the check.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code ignoreInlineTags} - Control whether
 * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html#CHDBEFIF">
 * inline tags</a> must be ignored.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;SingleLineJavadoc&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * &#47;** &#64;see Math *&#47; // violation, javadoc should be multiline
 * public int foo() {
 *   return 42;
 * }
 *
 * &#47;**
 *  * &#64;return 42
 *  *&#47;  // ok
 * public int bar() {
 *   return 42;
 * }
 *
 * &#47;** {&#64;link #equals(Object)} *&#47; // ok
 * public int baz() {
 *   return 42;
 * }
 *
 * &#47;**
 *  * &lt;p&gt;the answer to the ultimate question
 *  *&#47; // ok
 * public int magic() {
 *   return 42;
 * }
 *
 * &#47;**
 *  * &lt;p&gt;the answer to the ultimate question&lt;/p&gt;
 *  *&#47; // ok
 * public int foobar() {
 *   return 42;
 * }
 * </pre>
 * <p>
 * To configure the check with a list of ignored block tags:
 * </p>
 * <pre>
 * &lt;module name=&quot;SingleLineJavadoc&quot;&gt;
 *   &lt;property name=&quot;ignoredTags&quot; value=&quot;&#64;inheritDoc, &#64;see&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * &#47;** &#64;see Math *&#47; // ok
 * public int foo() {
 *   return 42;
 * }
 *
 * &#47;**
 *  * &#64;return 42
 *  *&#47;  // ok
 * public int bar() {
 *   return 42;
 * }
 *
 * &#47;** {&#64;link #equals(Object)} *&#47; // ok
 * public int baz() {
 *   return 42;
 * }
 *
 * &#47;**
 *  * &lt;p&gt;the answer to the ultimate question
 *  *&#47; // ok
 * public int magic() {
 *   return 42;
 * }
 *
 * &#47;**
 *  * &lt;p&gt;the answer to the ultimate question&lt;/p&gt;
 *  *&#47; // ok
 * public int foobar() {
 *   return 42;
 * }
 * </pre>
 * <p>
 * To configure the check to not ignore inline tags:
 * </p>
 * <pre>
 * &lt;module name=&quot;SingleLineJavadoc&quot;&gt;
 *   &lt;property name=&quot;ignoreInlineTags&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * &#47;** &#64;see Math *&#47; // violation, javadoc should be multiline
 * public int foo() {
 *   return 42;
 * }
 *
 * &#47;**
 *  * &#64;return 42
 *  *&#47;  // ok
 * public int bar() {
 *   return 42;
 * }
 *
 * &#47;** {&#64;link #equals(Object)} *&#47; // violation, javadoc should be multiline
 * public int baz() {
 *   return 42;
 * }
 *
 * &#47;**
 *  * &lt;p&gt;the answer to the ultimate question
 *  *&#47; // ok
 * public int magic() {
 *   return 42;
 * }
 *
 * &#47;**
 *  * &lt;p&gt;the answer to the ultimate question&lt;/p&gt;
 *  *&#47; // ok
 * public int foobar() {
 *   return 42;
 * }
 * </pre>
 * <p>
 * To configure the check to report violations for Tight-HTML Rules:
 * </p>
 * <pre>
 * &lt;module name=&quot;SingleLineJavadoc&quot;&gt;
 *   &lt;property name=&quot;violateExecutionOnNonTightHtml&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * &#47;** &#64;see Math *&#47; // violation, javadoc should be multiline
 * public int foo() {
 *   return 42;
 * }
 *
 * &#47;**
 *  * &#64;return 42
 *  *&#47;  // ok
 * public int bar() {
 *   return 42;
 * }
 *
 * &#47;** {&#64;link #equals(Object)} *&#47; // ok
 * public int baz() {
 *   return 42;
 * }
 *
 * &#47;**
 *  * &lt;p&gt;the answer to the ultimate question
 *  *&#47; // violation, unclosed HTML tag found: p
 * public int magic() {
 *   return 42;
 * }
 *
 * &#47;**
 *  * &lt;p&gt;the answer to the ultimate question&lt;/p&gt;
 *  *&#47; // ok
 * public int foobar() {
 *   return 42;
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code javadoc.missed.html.close}
 * </li>
 * <li>
 * {@code javadoc.parse.rule.error}
 * </li>
 * <li>
 * {@code javadoc.wrong.singleton.html.tag}
 * </li>
 * <li>
 * {@code singleline.javadoc}
 * </li>
 * </ul>
 *
 * @since 6.0
 */
@StatelessCheck
public class SingleLineJavadocCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "singleline.javadoc";

    /**
     * Specify
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html#CHDBEFIF">
     * block tags</a> which are ignored by the check.
     */
    private Set<String> ignoredTags = Set.of();

    /**
     * Control whether
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html#CHDBEFIF">
     * inline tags</a> must be ignored.
     */
    private boolean ignoreInlineTags = true;

    /**
     * Setter to specify
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html#CHDBEFIF">
     * block tags</a> which are ignored by the check.
     *
     * @param tags to be ignored by check.
     */
    public void setIgnoredTags(String... tags) {
        ignoredTags = Set.of(tags);
    }

    /**
     * Setter to control whether
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html#CHDBEFIF">
     * inline tags</a> must be ignored.
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
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (isSingleLineJavadoc(getBlockCommentAst())
                && (hasJavadocTags(ast) || !ignoreInlineTags && hasJavadocInlineTags(ast))) {
            log(ast.getLineNumber(), MSG_KEY);
        }
    }

    /**
     * Checks if comment is single-line comment.
     *
     * @param blockCommentStart the AST tree in which a block comment starts
     * @return true, if comment is single-line comment.
     */
    private static boolean isSingleLineJavadoc(DetailAST blockCommentStart) {
        final DetailAST blockCommentEnd = blockCommentStart.getLastChild();
        return TokenUtil.areOnSameLine(blockCommentStart, blockCommentEnd);
    }

    /**
     * Checks if comment has javadoc tags which are not ignored. Also works
     * on custom tags. As block tags can be interpreted only at the beginning of a line,
     * only the first instance is checked.
     *
     * @param javadocRoot javadoc root node.
     * @return true, if comment has javadoc tags which are not ignored.
     * @see <a href=
     * "https://docs.oracle.com/javase/7/docs/technotes/tools/windows/javadoc.html#blockandinlinetags">
     * Block and inline tags</a>
     */
    private boolean hasJavadocTags(DetailNode javadocRoot) {
        final DetailNode javadocTagSection =
                JavadocUtil.findFirstToken(javadocRoot, JavadocTokenTypes.JAVADOC_TAG);
        return javadocTagSection != null && !isTagIgnored(javadocTagSection);
    }

    /**
     * Checks if comment has in-line tags which are not ignored.
     *
     * @param javadocRoot javadoc root node.
     * @return true, if comment has in-line tags which are not ignored.
     * @see <a href=
     * "https://docs.oracle.com/javase/7/docs/technotes/tools/windows/javadoc.html#javadoctags">
     * JavadocTags</a>
     */
    private boolean hasJavadocInlineTags(DetailNode javadocRoot) {
        DetailNode javadocTagSection =
                JavadocUtil.findFirstToken(javadocRoot, JavadocTokenTypes.JAVADOC_INLINE_TAG);
        boolean foundTag = false;
        while (javadocTagSection != null) {
            if (!isTagIgnored(javadocTagSection)) {
                foundTag = true;
                break;
            }
            javadocTagSection = JavadocUtil.getNextSibling(
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
        return ignoredTags.contains(JavadocUtil.getTagName(javadocTagSection));
    }

}
