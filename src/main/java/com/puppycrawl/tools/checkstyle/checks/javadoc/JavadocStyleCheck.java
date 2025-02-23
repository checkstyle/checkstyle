///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static org.junit.Assert.assertThat;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser;
import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <div>
 * Validates Javadoc comments to help ensure they are well formed.
 * </div>
 *
 * <p>
 * The following checks are performed:
 * </p>
 * <ul>
 * <li>
 * Ensures the first sentence ends with proper punctuation
 * (That is a period, question mark, or exclamation mark, by default).
 * Note that this check is not applied to inline {@code @return} tags,
 * because the Javadoc tools automatically appends a period to the end of the tag
 * content.
 * Javadoc automatically places the first sentence in the method summary
 * table and index. Without proper punctuation the Javadoc may be malformed.
 * All items eligible for the {@code {@inheritDoc}} tag are exempt from this
 * requirement.
 * </li>
 * <li>
 * Check text for Javadoc statements that do not have any description.
 * This includes both completely empty Javadoc, and Javadoc with only tags
 * such as {@code @param} and {@code @return}.
 * </li>
 * <li>
 * Check text for incomplete HTML tags. Verifies that HTML tags have
 * corresponding end tags and issues an "Unclosed HTML tag found:" error if not.
 * An "Extra HTML tag found:" error is issued if an end tag is found without
 * a previous open tag.
 * </li>
 * <li>
 * Check that a package Javadoc comment is well-formed (as described above).
 * </li>
 * <li>
 * Check for allowed HTML tags. The list of allowed HTML tags is
 * "a", "abbr", "acronym", "address", "area", "b", "bdo", "big", "blockquote",
 * "br", "caption", "cite", "code", "colgroup", "dd", "del", "dfn", "div", "dl",
 * "dt", "em", "fieldset", "font", "h1", "h2", "h3", "h4", "h5", "h6", "hr",
 * "i", "img", "ins", "kbd", "li", "ol", "p", "pre", "q", "samp", "small",
 * "span", "strong", "sub", "sup", "table", "tbody", "td", "tfoot", "th",
 * "thead", "tr", "tt", "u", "ul", "var".
 * </li>
 * </ul>
 *
 * <p>
 * These checks were patterned after the checks made by the
 * <a href="https://maven-doccheck.sourceforge.net">DocCheck</a> doclet
 * available from Sun. Note: Original Sun's DocCheck tool does not exist anymore.
 * </p>
 * <ul>
 * <li>
 * Property {@code checkEmptyJavadoc} - Control whether to check if the Javadoc
 * is missing a describing text.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code checkFirstSentence} - Control whether to check the first
 * sentence for proper end of sentence.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code checkHtml} - Control whether to check for incomplete HTML tags.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code endOfSentenceFormat} - Specify the format for matching
 * the end of a sentence.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "([.?!][ \t\n\r\f&lt;])|([.?!]$)"}.
 * </li>
 * <li>
 * Property {@code excludeScope} - Specify the visibility scope where
 * Javadoc comments are not checked.
 * Type is {@code com.puppycrawl.tools.checkstyle.api.Scope}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code scope} - Specify the visibility scope where Javadoc comments are checked.
 * Type is {@code com.puppycrawl.tools.checkstyle.api.Scope}.
 * Default value is {@code private}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_DEF">
 * ANNOTATION_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_FIELD_DEF">
 * ANNOTATION_FIELD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_CONSTANT_DEF">
 * ENUM_CONSTANT_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#PACKAGE_DEF">
 * PACKAGE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#VARIABLE_DEF">
 * VARIABLE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COMPACT_CTOR_DEF">
 * COMPACT_CTOR_DEF</a>.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code javadoc.empty}
 * </li>
 * <li>
 * {@code javadoc.extraHtml}
 * </li>
 * <li>
 * {@code javadoc.incompleteTag}
 * </li>
 * <li>
 * {@code javadoc.noPeriod}
 * </li>
 * <li>
 * {@code javadoc.unclosedHtml}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@StatelessCheck
public class JavadocStyleCheck
    extends AbstractCheck {

    /** Message property key for the Empty Javadoc message. */
    public static final String MSG_EMPTY = "javadoc.empty";

    /** Message property key for the No Javadoc end of Sentence Period message. */
    public static final String MSG_NO_PERIOD = "javadoc.noPeriod";

    /** Message property key for the Incomplete Tag message. */
    public static final String MSG_INCOMPLETE_TAG = "javadoc.incompleteTag";

    /** Message property key for the Unclosed HTML message. */
    public static final String MSG_UNCLOSED_HTML = JavadocDetailNodeParser.MSG_UNCLOSED_HTML_TAG;

    /** Message property key for the Extra HTML message. */
    public static final String MSG_EXTRA_HTML = "javadoc.extraHtml";

    /** HTML tags that do not require a close tag. */
    private static final Set<String> SINGLE_TAGS = Set.of(
        "br", "li", "dt", "dd", "hr", "img", "p", "td", "tr", "th"
    );

    /**
     * HTML tags that are allowed in java docs.
     * From <a href="https://www.w3schools.com/tags/default.asp">w3schools</a>:
     * <br>
     * The forms and structure tags are not allowed
     */
    private static final Set<String> ALLOWED_TAGS = Set.of(
        "a", "abbr", "acronym", "address", "area", "b", "bdo", "big",
        "blockquote", "br", "caption", "cite", "code", "colgroup", "dd",
        "del", "dfn", "div", "dl", "dt", "em", "fieldset", "font", "h1",
        "h2", "h3", "h4", "h5", "h6", "hr", "i", "img", "ins", "kbd",
        "li", "ol", "p", "pre", "q", "samp", "small", "span", "strong",
        "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead",
        "tr", "tt", "u", "ul", "var"
    );

    /** Specify the format for inline return Javadoc. */
    private static final Pattern INLINE_RETURN_TAG_PATTERN =
            Pattern.compile("\\{@return.*?}\\s*");

    /** Specify the format for first word in javadoc. */
    private static final Pattern SENTENCE_SEPARATOR = Pattern.compile("\\.(?=\\s|$)");

    /** Specify the visibility scope where Javadoc comments are checked. */
    private Scope scope = Scope.PRIVATE;

    /** Specify the visibility scope where Javadoc comments are not checked. */
    private Scope excludeScope;

    /** Specify the format for matching the end of a sentence. */
    private Pattern endOfSentenceFormat = Pattern.compile("([.?!][ \t\n\r\f<])|([.?!]$)");

    /**
     * Control whether to check the first sentence for proper end of sentence.
     */
    private boolean checkFirstSentence = true;

    /**
     * Control whether to check for incomplete HTML tags.
     */
    private boolean checkHtml = true;

    /**
     * Control whether to check if the Javadoc is missing a describing text.
     */
    private boolean checkEmptyJavadoc;

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    // suppress deprecation until https://github.com/checkstyle/checkstyle/issues/11166
    @SuppressWarnings("deprecation")
    @Override
    public void visitToken(DetailAST ast) {
        if (shouldCheck(ast)) {
            final FileContents contents = getFileContents();
            // Need to start searching for the comment before the annotations
            // that may exist. Even if annotations are not defined on the
            // package, the ANNOTATIONS AST is defined.
            final TextBlock textBlock =
                contents.getJavadocBefore(ast.getFirstChild().getLineNo());

            checkComment(ast, textBlock);
        }
    }

    /**
     * Whether we should check this node.
     *
     * @param ast a given node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(final DetailAST ast) {
        boolean check = false;

        if (ast.getType() == TokenTypes.PACKAGE_DEF) {
            check = CheckUtil.isPackageInfo(getFilePath());
        }
        else if (!ScopeUtil.isInCodeBlock(ast)) {
            final Scope customScope = ScopeUtil.getScope(ast);
            final Scope surroundingScope = ScopeUtil.getSurroundingScope(ast);

            check = customScope.isIn(scope)
                    && surroundingScope.isIn(scope)
                    && (excludeScope == null || !customScope.isIn(excludeScope)
                            || !surroundingScope.isIn(excludeScope));
        }
        return check;
    }

    /**
     * Performs the various checks against the Javadoc comment.
     *
     * @param ast the AST of the element being documented
     * @param comment the source lines that make up the Javadoc comment.
     *
     * @see #checkFirstSentenceEnding(DetailAST, TextBlock)
     * @see #checkHtmlTags(DetailAST, TextBlock)
     */
    private void checkComment(final DetailAST ast, final TextBlock comment) {
        if (comment != null) {
            if (checkFirstSentence) {
                checkFirstSentenceEnding(ast, comment);
            }

            if (checkHtml) {
                checkHtmlTags(ast, comment);
            }

            if (checkEmptyJavadoc) {
                checkJavadocIsNotEmpty(comment);
            }
        }
    }

    /**
     * Checks that the first sentence ends with proper punctuation.  This method
     * uses a regular expression that checks for the presence of a period,
     * question mark, or exclamation mark followed either by whitespace, an
     * HTML element, or the end of string. This method ignores {_AT_inheritDoc}
     * comments for TokenTypes that are valid for {_AT_inheritDoc}.
     *
     * @param ast the current node
     * @param comment the source lines that make up the Javadoc comment.
     */
    private void checkFirstSentenceEnding(final DetailAST ast, TextBlock comment) {
        final String commentText = getCommentText(comment.getText());
        checkTrailingSpaces(comment, commentText);
        final boolean hasInLineReturnTag = Arrays.stream(SENTENCE_SEPARATOR.split(commentText))
                .findFirst()
                .map(INLINE_RETURN_TAG_PATTERN::matcher)
                .filter(Matcher::find)
                .isPresent();

        if (!hasInLineReturnTag
            && !commentText.isEmpty()
            && !endOfSentenceFormat.matcher(commentText).find()
            && !(commentText.startsWith("{@inheritDoc}")
            && JavadocTagInfo.INHERIT_DOC.isValidOn(ast))) {
            log(comment.getStartLineNo(), MSG_NO_PERIOD);
        }
    }

    /**
     * Checks that the Javadoc is not empty.
     *
     * @param comment the source lines that make up the Javadoc comment.
     */
    private void checkJavadocIsNotEmpty(TextBlock comment) {
        final String commentText = getCommentText(comment.getText());
        checkTrailingSpaces(comment, commentText);

        if (commentText.isEmpty()) {
            log(comment.getStartLineNo(), MSG_EMPTY);
        }
    }

    /**
     * Checks that the trailing space is removed.
     *
     * @param comment the source lines that make up the Javadoc comment.
     * @param commentText the text form of comment
     */
    private void checkTrailingSpaces(TextBlock comment, String commentText) {
        String[] lines = commentText.split("\n");

        // Ensure no line ends with a space
        for (String line : lines) {
            // error if trailing space will present
            if(line.endsWith(" ")){}
        }
    }

    /**
     * Returns the comment text from the Javadoc.
     *
     * @param comments the lines of Javadoc.
     * @return a comment text String.
     */
    private static String getCommentText(String... comments) {
        final StringBuilder builder = new StringBuilder(1024);
        for (final String line : comments) {
            final int textStart = findTextStart(line);

            if (textStart != -1) {
                if (line.charAt(textStart) == '@') {
                    // we have found the tag section
                    break;
                }
                builder.append(line.substring(textStart));
                trimTail(builder);
                builder.append('\n');
            }
        }

        return builder.toString().trim();
    }

    /**
     * Finds the index of the first non-whitespace character ignoring the
     * Javadoc comment start and end strings (&#47;** and *&#47;) as well as any
     * leading asterisk.
     *
     * @param line the Javadoc comment line of text to scan.
     * @return the int index relative to 0 for the start of text
     *         or -1 if not found.
     */
    private static int findTextStart(String line) {
        int textStart = -1;
        int index = 0;
        while (index < line.length()) {
            if (!Character.isWhitespace(line.charAt(index))) {
                if (line.regionMatches(index, "/**", 0, "/**".length())
                    || line.regionMatches(index, "*/", 0, 2)) {
                    index++;
                }
                else if (line.charAt(index) != '*') {
                    textStart = index;
                    break;
                }
            }
            index++;
        }
        return textStart;
    }

    /**
     * Trims any trailing whitespace or the end of Javadoc comment string.
     *
     * @param builder the StringBuilder to trim.
     */
    private static void trimTail(StringBuilder builder) {
        int index = builder.length() - 1;
        while (true) {
            if (Character.isWhitespace(builder.charAt(index))) {
                builder.deleteCharAt(index);
            }
            else if (index > 0 && builder.charAt(index) == '/'
                    && builder.charAt(index - 1) == '*') {
                builder.deleteCharAt(index);
                builder.deleteCharAt(index - 1);
                index--;
                while (builder.charAt(index - 1) == '*') {
                    builder.deleteCharAt(index - 1);
                    index--;
                }
            }
            else {
                break;
            }
            index--;
        }
    }

    /**
     * Checks the comment for HTML tags that do not have a corresponding close
     * tag or a close tag that has no previous open tag.  This code was
     * primarily copied from the DocCheck checkHtml method.
     *
     * @param ast the node with the Javadoc
     * @param comment the {@code TextBlock} which represents
     *                 the Javadoc comment.
     * @noinspection MethodWithMultipleReturnPoints
     * @noinspectionreason MethodWithMultipleReturnPoints - check and method are
     *      too complex to break apart
     */
    // -@cs[ReturnCount] Too complex to break apart.
    private void checkHtmlTags(final DetailAST ast, final TextBlock comment) {
        final int lineNo = comment.getStartLineNo();
        final Deque<HtmlTag> htmlStack = new ArrayDeque<>();
        final String[] text = comment.getText();

        final TagParser parser = new TagParser(text, lineNo);

        while (parser.hasNextTag()) {
            final HtmlTag tag = parser.nextTag();

            if (tag.isIncompleteTag()) {
                log(tag.getLineNo(), MSG_INCOMPLETE_TAG,
                    text[tag.getLineNo() - lineNo]);
                return;
            }
            if (tag.isClosedTag()) {
                // do nothing
                continue;
            }
            if (tag.isCloseTag()) {
                // We have found a close tag.
                if (isExtraHtml(tag.getId(), htmlStack)) {
                    // No corresponding open tag was found on the stack.
                    log(tag.getLineNo(),
                        tag.getPosition(),
                        MSG_EXTRA_HTML,
                        tag.getText());
                }
                else {
                    // See if there are any unclosed tags that were opened
                    // after this one.
                    checkUnclosedTags(htmlStack, tag.getId());
                }
            }
            else {
                // We only push html tags that are allowed
                if (isAllowedTag(tag)) {
                    htmlStack.push(tag);
                }
            }
        }

        // Identify any tags left on the stack.
        // Skip multiples, like <b>...<b>
        String lastFound = "";
        final List<String> typeParameters = CheckUtil.getTypeParameterNames(ast);
        for (final HtmlTag htmlTag : htmlStack) {
            if (!isSingleTag(htmlTag)
                && !htmlTag.getId().equals(lastFound)
                && !typeParameters.contains(htmlTag.getId())) {
                log(htmlTag.getLineNo(), htmlTag.getPosition(),
                        MSG_UNCLOSED_HTML, htmlTag.getText());
                lastFound = htmlTag.getId();
            }
        }
    }

    /**
     * Checks to see if there are any unclosed tags on the stack.  The token
     * represents a html tag that has been closed and has a corresponding open
     * tag on the stack.  Any tags, except single tags, that were opened
     * (pushed on the stack) after the token are missing a close.
     *
     * @param htmlStack the stack of opened HTML tags.
     * @param token the current HTML tag name that has been closed.
     */
    private void checkUnclosedTags(Deque<HtmlTag> htmlStack, String token) {
        final Deque<HtmlTag> unclosedTags = new ArrayDeque<>();
        HtmlTag lastOpenTag = htmlStack.pop();
        while (!token.equalsIgnoreCase(lastOpenTag.getId())) {
            // Find unclosed elements. Put them on a stack so the
            // output order won't be back-to-front.
            if (isSingleTag(lastOpenTag)) {
                lastOpenTag = htmlStack.pop();
            }
            else {
                unclosedTags.push(lastOpenTag);
                lastOpenTag = htmlStack.pop();
            }
        }

        // Output the unterminated tags, if any
        // Skip multiples, like <b>..<b>
        String lastFound = "";
        for (final HtmlTag htag : unclosedTags) {
            lastOpenTag = htag;
            if (lastOpenTag.getId().equals(lastFound)) {
                continue;
            }
            lastFound = lastOpenTag.getId();
            log(lastOpenTag.getLineNo(),
                lastOpenTag.getPosition(),
                MSG_UNCLOSED_HTML,
                lastOpenTag.getText());
        }
    }

    /**
     * Determines if the HtmlTag is one which does not require a close tag.
     *
     * @param tag the HtmlTag to check.
     * @return {@code true} if the HtmlTag is a single tag.
     */
    private static boolean isSingleTag(HtmlTag tag) {
        // If it's a singleton tag (<p>, <br>, etc.), ignore it
        // Can't simply not put them on the stack, since singletons
        // like <dt> and <dd> (unhappily) may either be terminated
        // or not terminated. Both options are legal.
        return SINGLE_TAGS.contains(tag.getId().toLowerCase(Locale.ENGLISH));
    }

    /**
     * Determines if the HtmlTag is one which is allowed in a javadoc.
     *
     * @param tag the HtmlTag to check.
     * @return {@code true} if the HtmlTag is an allowed html tag.
     */
    private static boolean isAllowedTag(HtmlTag tag) {
        return ALLOWED_TAGS.contains(tag.getId().toLowerCase(Locale.ENGLISH));
    }

    /**
     * Determines if the given token is an extra HTML tag. This indicates that
     * a close tag was found that does not have a corresponding open tag.
     *
     * @param token an HTML tag id for which a close was found.
     * @param htmlStack a Stack of previous open HTML tags.
     * @return {@code false} if a previous open tag was found
     *         for the token.
     */
    private static boolean isExtraHtml(String token, Deque<HtmlTag> htmlStack) {
        boolean isExtra = true;
        for (final HtmlTag tag : htmlStack) {
            // Loop, looking for tags that are closed.
            // The loop is needed in case there are unclosed
            // tags on the stack. In that case, the stack would
            // not be empty, but this tag would still be extra.
            if (token.equalsIgnoreCase(tag.getId())) {
                isExtra = false;
                break;
            }
        }

        return isExtra;
    }

    /**
     * Setter to specify the visibility scope where Javadoc comments are checked.
     *
     * @param scope a scope.
     * @since 3.2
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * Setter to specify the visibility scope where Javadoc comments are not checked.
     *
     * @param excludeScope a scope.
     * @since 3.4
     */
    public void setExcludeScope(Scope excludeScope) {
        this.excludeScope = excludeScope;
    }

    /**
     * Setter to specify the format for matching the end of a sentence.
     *
     * @param pattern a pattern.
     * @since 5.0
     */
    public void setEndOfSentenceFormat(Pattern pattern) {
        endOfSentenceFormat = pattern;
    }

    /**
     * Setter to control whether to check the first sentence for proper end of sentence.
     *
     * @param flag {@code true} if the first sentence is to be checked
     * @since 3.2
     */
    public void setCheckFirstSentence(boolean flag) {
        checkFirstSentence = flag;
    }

    /**
     * Setter to control whether to check for incomplete HTML tags.
     *
     * @param flag {@code true} if HTML checking is to be performed.
     * @since 3.2
     */
    public void setCheckHtml(boolean flag) {
        checkHtml = flag;
    }

    /**
     * Setter to control whether to check if the Javadoc is missing a describing text.
     *
     * @param flag {@code true} if empty Javadoc checking should be done.
     * @since 3.4
     */
    public void setCheckEmptyJavadoc(boolean flag) {
        checkEmptyJavadoc = flag;
    }

}
