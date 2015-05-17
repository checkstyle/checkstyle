////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import com.google.common.collect.ImmutableSortedSet;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.CheckUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Custom Checkstyle Check to validate Javadoc.
 *
 * @author Chris Stillwell
 * @author Daniel Grenner
 * @author Travis Schneeberger
 */
public class JavadocStyleCheck
    extends Check {

    /** Message property key for the Unclosed HTML message. */
    public static final String JAVADOC_MISSING = "javadoc.missing";

    /** Message property key for the Unclosed HTML message. */
    public static final String EMPTY = "javadoc.empty";

    /** Message property key for the Unclosed HTML message. */
    public static final String NO_PERIOD = "javadoc.noperiod";

    /** Message property key for the Unclosed HTML message. */
    public static final String INCOMPLETE_TAG = "javadoc.incompleteTag";

    /** Message property key for the Unclosed HTML message. */
    public static final String UNCLOSED_HTML = "javadoc.unclosedhtml";

    /** Message property key for the Extra HTML message. */
    public static final String EXTRA_HTML = "javadoc.extrahtml";

    /** HTML tags that do not require a close tag. */
    private static final Set<String> SINGLE_TAGS = ImmutableSortedSet.of(
            "br", "li", "dt", "dd", "hr", "img", "p", "td", "tr", "th");

    /** HTML tags that are allowed in java docs.
     * From http://www.w3schools.com/tags/default.asp
     * The froms and structure tags are not allowed
     */
    private static final Set<String> ALLOWED_TAGS = ImmutableSortedSet.of(
            "a", "abbr", "acronym", "address", "area", "b", "bdo", "big",
            "blockquote", "br", "caption", "cite", "code", "colgroup", "dd",
            "del", "div", "dfn", "dl", "dt", "em", "fieldset", "font", "h1",
            "h2", "h3", "h4", "h5", "h6", "hr", "i", "img", "ins", "kbd",
            "li", "ol", "p", "pre", "q", "samp", "small", "span", "strong",
            "style", "sub", "sup", "table", "tbody", "td", "tfoot", "th",
            "thead", "tr", "tt", "u", "ul");

    /** The scope to check. */
    private Scope scope = Scope.PRIVATE;

    /** the visibility scope where Javadoc comments shouldn't be checked **/
    private Scope excludeScope;

    /** Format for matching the end of a sentence. */
    private String endOfSentenceFormat = "([.?!][ \t\n\r\f<])|([.?!]$)";

    /** Regular expression for matching the end of a sentence. */
    private Pattern endOfSentencePattern;

    /**
     * Indicates if the first sentence should be checked for proper end of
     * sentence punctuation.
     */
    private boolean checkingFirstSentence = true;

    /**
     * Indicates if the HTML within the comment should be checked.
     */
    private boolean checkingHtml = true;

    /**
     * Indicates if empty javadoc statements should be checked.
     */
    private boolean checkingEmptyJavadoc;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.PACKAGE_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.PACKAGE_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (shouldCheck(ast)) {
            final FileContents contents = getFileContents();
            // Need to start searching for the comment before the annotations
            // that may exist. Even if annotations are not defined on the
            // package, the ANNOTATIONS AST is defined.
            final TextBlock cmt =
                contents.getJavadocBefore(ast.getFirstChild().getLineNo());

            checkComment(ast, cmt);
        }
    }

    /**
     * Whether we should check this node.
     * @param ast a given node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(final DetailAST ast) {
        if (ast.getType() == TokenTypes.PACKAGE_DEF) {
            return getFileContents().inPackageInfo();
        }

        if (ScopeUtils.inCodeBlock(ast)) {
            return false;
        }

        final Scope declaredScope;
        if (ast.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
            declaredScope = Scope.PUBLIC;
        }
        else {
            declaredScope = ScopeUtils.getScopeFromMods(
                ast.findFirstToken(TokenTypes.MODIFIERS));
        }

        final Scope scope =
            ScopeUtils.inInterfaceOrAnnotationBlock(ast)
            ? Scope.PUBLIC : declaredScope;
        final Scope surroundingScope = ScopeUtils.getSurroundingScope(ast);

        return scope.isIn(this.scope)
            && (surroundingScope == null || surroundingScope.isIn(this.scope))
            && (excludeScope == null
                || !scope.isIn(excludeScope)
                || surroundingScope != null
                && !surroundingScope.isIn(excludeScope));
    }

    /**
     * Performs the various checks agains the Javadoc comment.
     *
     * @param ast the AST of the element being documented
     * @param comment the source lines that make up the Javadoc comment.
     *
     * @see #checkFirstSentence(DetailAST, TextBlock)
     * @see #checkHtml(DetailAST, TextBlock)
     */
    private void checkComment(final DetailAST ast, final TextBlock comment) {
        if (comment == null) {
            /*checking for missing docs in JavadocStyleCheck is not consistent
            with the rest of CheckStyle...  Even though, I didn't think it
            made sense to make another csheck just to ensure that the
            package-info.java file actually contains package Javadocs.*/
            if (getFileContents().inPackageInfo()) {
                log(ast.getLineNo(), JAVADOC_MISSING);
            }
            return;
        }

        if (checkingFirstSentence) {
            checkFirstSentence(ast, comment);
        }

        if (checkingHtml) {
            checkHtml(ast, comment);
        }

        if (checkingEmptyJavadoc) {
            checkEmptyJavadoc(comment);
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
    private void checkFirstSentence(final DetailAST ast, TextBlock comment) {
        final String commentText = getCommentText(comment.getText());

        if (commentText.length() != 0
            && !getEndOfSentencePattern().matcher(commentText).find()
            && !("{@inheritDoc}".equals(commentText)
            && JavadocTagInfo.INHERIT_DOC.isValidOn(ast))) {
            log(comment.getStartLineNo(), NO_PERIOD);
        }
    }

    /**
     * Checks that the Javadoc is not empty.
     *
     * @param comment the source lines that make up the Javadoc comment.
     */
    private void checkEmptyJavadoc(TextBlock comment) {
        final String commentText = getCommentText(comment.getText());

        if (commentText.length() == 0) {
            log(comment.getStartLineNo(), EMPTY);
        }
    }

    /**
     * Returns the comment text from the Javadoc.
     * @param comments the lines of Javadoc.
     * @return a comment text String.
     */
    private String getCommentText(String... comments) {
        final StringBuffer buffer = new StringBuffer();
        for (final String line : comments) {
            final int textStart = findTextStart(line);

            if (textStart != -1) {
                if (line.charAt(textStart) == '@') {
                    //we have found the tag section
                    break;
                }
                buffer.append(line.substring(textStart));
                trimTail(buffer);
                buffer.append('\n');
            }
        }

        return buffer.toString().trim();
    }

    /**
     * Finds the index of the first non-whitespace character ignoring the
     * Javadoc comment start and end strings (&#47** and *&#47) as well as any
     * leading asterisk.
     * @param line the Javadoc comment line of text to scan.
     * @return the int index relative to 0 for the start of text
     *         or -1 if not found.
     */
    private int findTextStart(String line) {
        int textStart = -1;
        for (int i = 0; i < line.length(); i++) {
            if (!Character.isWhitespace(line.charAt(i))) {
                if (line.regionMatches(i, "/**", 0, "/**".length())) {
                    i += 2;
                }
                else if (line.regionMatches(i, "*/", 0, 2)) {
                    i++;
                }
                else if (line.charAt(i) != '*') {
                    textStart = i;
                    break;
                }
            }
        }
        return textStart;
    }

    /**
     * Trims any trailing whitespace or the end of Javadoc comment string.
     * @param buffer the StringBuffer to trim.
     */
    private void trimTail(StringBuffer buffer) {
        for (int i = buffer.length() - 1; i >= 0; i--) {
            if (Character.isWhitespace(buffer.charAt(i))) {
                buffer.deleteCharAt(i);
            }
            else if (i > 0
                     && buffer.charAt(i - 1) == '*'
                     && buffer.charAt(i) == '/') {
                buffer.deleteCharAt(i);
                buffer.deleteCharAt(i - 1);
                i--;
                while (buffer.charAt(i - 1) == '*') {
                    buffer.deleteCharAt(i - 1);
                    i--;
                }
            }
            else {
                break;
            }
        }
    }

    /**
     * Checks the comment for HTML tags that do not have a corresponding close
     * tag or a close tag that has no previous open tag.  This code was
     * primarily copied from the DocCheck checkHtml method.
     *
     * @param ast the node with the Javadoc
     * @param comment the <code>TextBlock</code> which represents
     *                 the Javadoc comment.
     */
    private void checkHtml(final DetailAST ast, final TextBlock comment) {
        final int lineno = comment.getStartLineNo();
        final Deque<HtmlTag> htmlStack = new ArrayDeque<>();
        final String[] text = comment.getText();

        TagParser parser = null;
        parser = new TagParser(text, lineno);

        while (parser.hasNextTag()) {
            final HtmlTag tag = parser.nextTag();

            if (tag.isIncompleteTag()) {
                log(tag.getLineNo(), INCOMPLETE_TAG,
                    text[tag.getLineNo() - lineno]);
                return;
            }
            if (tag.isClosedTag()) {
                //do nothing
                continue;
            }
            if (!tag.isCloseTag()) {
                //We only push html tags that are allowed
                if (isAllowedTag(tag)) {
                    htmlStack.push(tag);
                }
            }
            else {
                // We have found a close tag.
                if (isExtraHtml(tag.getId(), htmlStack)) {
                    // No corresponding open tag was found on the stack.
                    log(tag.getLineNo(),
                        tag.getPosition(),
                        EXTRA_HTML,
                        tag);
                }
                else {
                    // See if there are any unclosed tags that were opened
                    // after this one.
                    checkUnclosedTags(htmlStack, tag.getId());
                }
            }
        }

        // Identify any tags left on the stack.
        String lastFound = ""; // Skip multiples, like <b>...<b>
        final List<String> typeParameters = CheckUtils.getTypeParameterNames(ast);
        for (final HtmlTag htag : htmlStack) {
            if (!isSingleTag(htag)
                && !htag.getId().equals(lastFound)
                && !typeParameters.contains(htag.getId())) {
                log(htag.getLineNo(), htag.getPosition(), UNCLOSED_HTML, htag);
                lastFound = htag.getId();
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
        String lastFound = ""; // Skip multiples, like <b>..<b>
        for (final HtmlTag htag : unclosedTags) {
            lastOpenTag = htag;
            if (lastOpenTag.getId().equals(lastFound)) {
                continue;
            }
            lastFound = lastOpenTag.getId();
            log(lastOpenTag.getLineNo(),
                lastOpenTag.getPosition(),
                UNCLOSED_HTML,
                lastOpenTag);
        }
    }

    /**
     * Determines if the HtmlTag is one which does not require a close tag.
     *
     * @param tag the HtmlTag to check.
     * @return <code>true</code> if the HtmlTag is a single tag.
     */
    private boolean isSingleTag(HtmlTag tag) {
        // If its a singleton tag (<p>, <br>, etc.), ignore it
        // Can't simply not put them on the stack, since singletons
        // like <dt> and <dd> (unhappily) may either be terminated
        // or not terminated. Both options are legal.
        return SINGLE_TAGS.contains(tag.getId().toLowerCase(Locale.ENGLISH));
    }

    /**
     * Determines if the HtmlTag is one which is allowed in a javadoc.
     *
     * @param tag the HtmlTag to check.
     * @return <code>true</code> if the HtmlTag is an allowed html tag.
     */
    private boolean isAllowedTag(HtmlTag tag) {
        return ALLOWED_TAGS.contains(tag.getId().toLowerCase(Locale.ENGLISH));
    }

    /**
     * Determines if the given token is an extra HTML tag. This indicates that
     * a close tag was found that does not have a corresponding open tag.
     *
     * @param token an HTML tag id for which a close was found.
     * @param htmlStack a Stack of previous open HTML tags.
     * @return <code>false</code> if a previous open tag was found
     *         for the token.
     */
    private boolean isExtraHtml(String token, Deque<HtmlTag> htmlStack) {
        boolean isExtra = true;
        for (final HtmlTag td : htmlStack) {
            // Loop, looking for tags that are closed.
            // The loop is needed in case there are unclosed
            // tags on the stack. In that case, the stack would
            // not be empty, but this tag would still be extra.
            if (token.equalsIgnoreCase(td.getId())) {
                isExtra = false;
                break;
            }
        }

        return isExtra;
    }

    /**
     * Sets the scope to check.
     * @param from string to get the scope from
     */
    public void setScope(String from) {
        scope = Scope.getInstance(from);
    }

    /**
     * Set the excludeScope.
     * @param scope a <code>String</code> value
     */
    public void setExcludeScope(String scope) {
        excludeScope = Scope.getInstance(scope);
    }

    /**
     * Set the format for matching the end of a sentence.
     * @param format format for matching the end of a sentence.
     */
    public void setEndOfSentenceFormat(String format) {
        endOfSentenceFormat = format;
    }

    /**
     * Returns a regular expression for matching the end of a sentence.
     *
     * @return a regular expression for matching the end of a sentence.
     */
    private Pattern getEndOfSentencePattern() {
        if (endOfSentencePattern == null) {
            endOfSentencePattern = Pattern.compile(endOfSentenceFormat);
        }
        return endOfSentencePattern;
    }

    /**
     * Sets the flag that determines if the first sentence is checked for
     * proper end of sentence punctuation.
     * @param flag <code>true</code> if the first sentence is to be checked
     */
    public void setCheckFirstSentence(boolean flag) {
        checkingFirstSentence = flag;
    }

    /**
     * Sets the flag that determines if HTML checking is to be performed.
     * @param flag <code>true</code> if HTML checking is to be performed.
     */
    public void setCheckHtml(boolean flag) {
        checkingHtml = flag;
    }

    /**
     * Sets the flag that determines if empty Javadoc checking should be done.
     * @param flag <code>true</code> if empty Javadoc checking should be done.
     */
    public void setCheckEmptyJavadoc(boolean flag) {
        checkingEmptyJavadoc = flag;
    }
}
