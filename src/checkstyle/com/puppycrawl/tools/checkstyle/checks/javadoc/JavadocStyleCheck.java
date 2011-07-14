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
package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.google.common.collect.ImmutableSortedSet;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FastStack;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.CheckUtils;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Custom Checkstyle Check to validate Javadoc.
 *
 * @author Chris Stillwell
 * @author Daniel Grenner
 * @author Travis Schneeberger
 * @version 1.2
 */
public class JavadocStyleCheck
    extends Check
{
    /** Message property key for the Unclosed HTML message. */
    private static final String UNCLOSED_HTML = "javadoc.unclosedhtml";

    /** Message property key for the Extra HTML message. */
    private static final String EXTRA_HTML = "javadoc.extrahtml";

    /** HTML tags that do not require a close tag. */
    private static final Set<String> SINGLE_TAGS = ImmutableSortedSet.of("p",
            "br", "li", "dt", "dd", "td", "hr", "img", "tr", "th", "td");

    /** HTML tags that are allowed in java docs.
     * From http://www.w3schools.com/tags/default.asp
     * The froms and structure tags are not allowed
     */
    private static final Set<String> ALLOWED_TAGS = ImmutableSortedSet.of(
            "a", "abbr", "acronym", "address", "area", "b", "bdo", "big",
            "blockquote", "br", "caption", "cite", "code", "colgroup", "del",
            "div", "dfn", "dl", "em", "fieldset", "h1", "h2", "h3", "h4", "h5",
            "h6", "hr", "i", "img", "ins", "kbd", "li", "ol", "p", "pre", "q",
            "samp", "small", "span", "strong", "style", "sub", "sup", "table",
            "tbody", "td", "tfoot", "th", "thead", "tr", "tt", "ul");

    /** The scope to check. */
    private Scope mScope = Scope.PRIVATE;

    /** the visibility scope where Javadoc comments shouldn't be checked **/
    private Scope mExcludeScope;

    /** Format for matching the end of a sentence. */
    private String mEndOfSentenceFormat = "([.?!][ \t\n\r\f<])|([.?!]$)";

    /** Regular expression for matching the end of a sentence. */
    private Pattern mEndOfSentencePattern;

    /**
     * Indicates if the first sentence should be checked for proper end of
     * sentence punctuation.
     */
    private boolean mCheckFirstSentence = true;

    /**
     * Indicates if the HTML within the comment should be checked.
     */
    private boolean mCheckHtml = true;

    /**
     * Indicates if empty javadoc statements should be checked.
     */
    private boolean mCheckEmptyJavadoc;

    @Override
    public int[] getDefaultTokens()
    {
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
    public void visitToken(DetailAST aAST)
    {
        if (shouldCheck(aAST)) {
            final FileContents contents = getFileContents();
            // Need to start searching for the comment before the annotations
            // that may exist. Even if annotations are not defined on the
            // package, the ANNOTATIONS AST is defined.
            final TextBlock cmt =
                contents.getJavadocBefore(aAST.getFirstChild().getLineNo());

            checkComment(aAST, cmt);
        }
    }

    /**
     * Whether we should check this node.
     * @param aAST a given node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(final DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.PACKAGE_DEF) {
            return getFileContents().inPackageInfo();
        }

        if (ScopeUtils.inCodeBlock(aAST)) {
            return false;
        }

        final Scope declaredScope;
        if (aAST.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
            declaredScope = Scope.PUBLIC;
        }
        else {
            declaredScope = ScopeUtils.getScopeFromMods(
                aAST.findFirstToken(TokenTypes.MODIFIERS));
        }

        final Scope scope =
            ScopeUtils.inInterfaceOrAnnotationBlock(aAST)
            ? Scope.PUBLIC : declaredScope;
        final Scope surroundingScope = ScopeUtils.getSurroundingScope(aAST);

        return scope.isIn(mScope)
            && ((surroundingScope == null) || surroundingScope.isIn(mScope))
            && ((mExcludeScope == null)
                || !scope.isIn(mExcludeScope)
                || ((surroundingScope != null)
                && !surroundingScope.isIn(mExcludeScope)));
    }

    /**
     * Performs the various checks agains the Javadoc comment.
     *
     * @param aAST the AST of the element being documented
     * @param aComment the source lines that make up the Javadoc comment.
     *
     * @see #checkFirstSentence(TextBlock)
     * @see #checkHtml(DetailAST, TextBlock)
     */
    private void checkComment(final DetailAST aAST, final TextBlock aComment)
    {
        if (aComment == null) {
            /*checking for missing docs in JavadocStyleCheck is not consistent
            with the rest of CheckStyle...  Even though, I didn't think it
            made sense to make another csheck just to ensure that the
            package-info.java file actually contains package Javadocs.*/
            if (getFileContents().inPackageInfo()) {
                log(aAST.getLineNo(), "javadoc.missing");
            }
            return;
        }

        if (mCheckFirstSentence) {
            checkFirstSentence(aAST, aComment);
        }

        if (mCheckHtml) {
            checkHtml(aAST, aComment);
        }

        if (mCheckEmptyJavadoc) {
            checkEmptyJavadoc(aComment);
        }
    }

    /**
     * Checks that the first sentence ends with proper punctuation.  This method
     * uses a regular expression that checks for the presence of a period,
     * question mark, or exclamation mark followed either by whitespace, an
     * HTML element, or the end of string. This method ignores {_AT_inheritDoc}
     * comments for TokenTypes that are valid for {_AT_inheritDoc}.
     *
     * @param aAST the current node
     * @param aComment the source lines that make up the Javadoc comment.
     */
    private void checkFirstSentence(final DetailAST aAST, TextBlock aComment)
    {
        final String commentText = getCommentText(aComment.getText());

        if ((commentText.length() != 0)
            && !getEndOfSentencePattern().matcher(commentText).find()
            && !("{@inheritDoc}".equals(commentText)
            && JavadocTagInfo.INHERIT_DOC.isValidOn(aAST)))
        {
            log(aComment.getStartLineNo(), "javadoc.noperiod");
        }
    }

    /**
     * Checks that the Javadoc is not empty.
     *
     * @param aComment the source lines that make up the Javadoc comment.
     */
    private void checkEmptyJavadoc(TextBlock aComment)
    {
        final String commentText = getCommentText(aComment.getText());

        if (commentText.length() == 0) {
            log(aComment.getStartLineNo(), "javadoc.empty");
        }
    }

    /**
     * Returns the comment text from the Javadoc.
     * @param aComments the lines of Javadoc.
     * @return a comment text String.
     */
    private String getCommentText(String[] aComments)
    {
        final StringBuffer buffer = new StringBuffer();
        for (final String line : aComments) {
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
     * @param aLine the Javadoc comment line of text to scan.
     * @return the int index relative to 0 for the start of text
     *         or -1 if not found.
     */
    private int findTextStart(String aLine)
    {
        int textStart = -1;
        for (int i = 0; i < aLine.length(); i++) {
            if (!Character.isWhitespace(aLine.charAt(i))) {
                if (aLine.regionMatches(i, "/**", 0, "/**".length())) {
                    i += 2;
                }
                else if (aLine.regionMatches(i, "*/", 0, 2)) {
                    i++;
                }
                else if (aLine.charAt(i) != '*') {
                    textStart = i;
                    break;
                }
            }
        }
        return textStart;
    }

    /**
     * Trims any trailing whitespace or the end of Javadoc comment string.
     * @param aBuffer the StringBuffer to trim.
     */
    private void trimTail(StringBuffer aBuffer)
    {
        for (int i = aBuffer.length() - 1; i >= 0; i--) {
            if (Character.isWhitespace(aBuffer.charAt(i))) {
                aBuffer.deleteCharAt(i);
            }
            else if ((i > 0)
                     && (aBuffer.charAt(i - 1) == '*')
                     && (aBuffer.charAt(i) == '/'))
            {
                aBuffer.deleteCharAt(i);
                aBuffer.deleteCharAt(i - 1);
                i--;
                while (aBuffer.charAt(i - 1) == '*') {
                    aBuffer.deleteCharAt(i - 1);
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
     * @param aAST the node with the Javadoc
     * @param aComment the <code>TextBlock</code> which represents
     *                 the Javadoc comment.
     */
    private void checkHtml(final DetailAST aAST, final TextBlock aComment)
    {
        final int lineno = aComment.getStartLineNo();
        final FastStack<HtmlTag> htmlStack = FastStack.newInstance();
        final String[] text = aComment.getText();
        final List<String> typeParameters =
            CheckUtils.getTypeParameterNames(aAST);

        TagParser parser = null;
        parser = new TagParser(text, lineno);

        while (parser.hasNextTag()) {
            final HtmlTag tag = parser.nextTag();

            if (tag.isIncompleteTag()) {
                log(tag.getLineno(), "javadoc.incompleteTag",
                    text[tag.getLineno() - lineno]);
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
                    log(tag.getLineno(),
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
        for (final HtmlTag htag : htmlStack) {
            if (!isSingleTag(htag)
                && !htag.getId().equals(lastFound)
                && !typeParameters.contains(htag.getId()))
            {
                log(htag.getLineno(), htag.getPosition(), UNCLOSED_HTML, htag);
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
     * @param aHtmlStack the stack of opened HTML tags.
     * @param aToken the current HTML tag name that has been closed.
     */
    private void checkUnclosedTags(FastStack<HtmlTag> aHtmlStack, String aToken)
    {
        final FastStack<HtmlTag> unclosedTags = FastStack.newInstance();
        HtmlTag lastOpenTag = aHtmlStack.pop();
        while (!aToken.equalsIgnoreCase(lastOpenTag.getId())) {
            // Find unclosed elements. Put them on a stack so the
            // output order won't be back-to-front.
            if (isSingleTag(lastOpenTag)) {
                lastOpenTag = aHtmlStack.pop();
            }
            else {
                unclosedTags.push(lastOpenTag);
                lastOpenTag = aHtmlStack.pop();
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
            log(lastOpenTag.getLineno(),
                lastOpenTag.getPosition(),
                UNCLOSED_HTML,
                lastOpenTag);
        }
    }

    /**
     * Determines if the HtmlTag is one which does not require a close tag.
     *
     * @param aTag the HtmlTag to check.
     * @return <code>true</code> if the HtmlTag is a single tag.
     */
    private boolean isSingleTag(HtmlTag aTag)
    {
        // If its a singleton tag (<p>, <br>, etc.), ignore it
        // Can't simply not put them on the stack, since singletons
        // like <dt> and <dd> (unhappily) may either be terminated
        // or not terminated. Both options are legal.
        return SINGLE_TAGS.contains(aTag.getId().toLowerCase());
    }

    /**
     * Determines if the HtmlTag is one which is allowed in a javadoc.
     *
     * @param aTag the HtmlTag to check.
     * @return <code>true</code> if the HtmlTag is an allowed html tag.
     */
    private boolean isAllowedTag(HtmlTag aTag)
    {
        return ALLOWED_TAGS.contains(aTag.getId().toLowerCase());
    }

    /**
     * Determines if the given token is an extra HTML tag. This indicates that
     * a close tag was found that does not have a corresponding open tag.
     *
     * @param aToken an HTML tag id for which a close was found.
     * @param aHtmlStack a Stack of previous open HTML tags.
     * @return <code>false</code> if a previous open tag was found
     *         for the token.
     */
    private boolean isExtraHtml(String aToken, FastStack<HtmlTag> aHtmlStack)
    {
        boolean isExtra = true;
        for (final HtmlTag td : aHtmlStack) {
            // Loop, looking for tags that are closed.
            // The loop is needed in case there are unclosed
            // tags on the stack. In that case, the stack would
            // not be empty, but this tag would still be extra.
            if (aToken.equalsIgnoreCase(td.getId())) {
                isExtra = false;
                break;
            }
        }

        return isExtra;
    }

    /**
     * Sets the scope to check.
     * @param aFrom string to get the scope from
     */
    public void setScope(String aFrom)
    {
        mScope = Scope.getInstance(aFrom);
    }

    /**
     * Set the excludeScope.
     * @param aScope a <code>String</code> value
     */
    public void setExcludeScope(String aScope)
    {
        mExcludeScope = Scope.getInstance(aScope);
    }

    /**
     * Set the format for matching the end of a sentence.
     * @param aFormat format for matching the end of a sentence.
     */
    public void setEndOfSentenceFormat(String aFormat)
    {
        mEndOfSentenceFormat = aFormat;
    }

    /**
     * Returns a regular expression for matching the end of a sentence.
     *
     * @return a regular expression for matching the end of a sentence.
     */
    private Pattern getEndOfSentencePattern()
    {
        if (mEndOfSentencePattern == null) {
            mEndOfSentencePattern = Pattern.compile(mEndOfSentenceFormat);
        }
        return mEndOfSentencePattern;
    }

    /**
     * Sets the flag that determines if the first sentence is checked for
     * proper end of sentence punctuation.
     * @param aFlag <code>true</code> if the first sentence is to be checked
     */
    public void setCheckFirstSentence(boolean aFlag)
    {
        mCheckFirstSentence = aFlag;
    }

    /**
     * Sets the flag that determines if HTML checking is to be performed.
     * @param aFlag <code>true</code> if HTML checking is to be performed.
     */
    public void setCheckHtml(boolean aFlag)
    {
        mCheckHtml = aFlag;
    }

    /**
     * Sets the flag that determines if empty JavaDoc checking should be done.
     * @param aFlag <code>true</code> if empty JavaDoc checking should be done.
     */
    public void setCheckEmptyJavadoc(boolean aFlag)
    {
        mCheckEmptyJavadoc = aFlag;
    }
}
