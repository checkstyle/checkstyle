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

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
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
 * Check that a package Javadoc comment is well-formed (as described above).
 * </li>
 * </ul>
 *
 * <p>
 * These checks were patterned after the checks made by the
 * <a href="https://maven-doccheck.sourceforge.net">DocCheck</a> doclet
 * available from Sun. Note: Original Sun's DocCheck tool does not exist anymore.
 * </p>
 *
 * @since 3.2
 */
@FileStatefulCheck
public class JavadocStyleCheck
    extends AbstractJavadocCheck {

    /** Message property key for the Empty Javadoc message. */
    public static final String MSG_EMPTY = "javadoc.empty";

    /** Message property key for the No Javadoc end of Sentence Period message. */
    public static final String MSG_NO_PERIOD = "javadoc.noPeriod";

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
     * Control whether to check if the Javadoc is missing a describing text.
     */
    private boolean checkEmptyJavadoc;

    /**
     * Control whether to validate first sentence text in Javadoc.
     */
    private boolean shouldValidateFirstSentence = true;

    /**
     * Control whether inherit doc tag is valid within a Javadoc block of a given AST.
     */
    private boolean inheritDocIsValid = true;

    @Override
    public int[] getDefaultJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.JAVADOC_CONTENT,
            JavadocCommentsTokenTypes.RETURN_INLINE_TAG,
        };
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (ast.getType() == JavadocCommentsTokenTypes.RETURN_INLINE_TAG) {
            shouldValidateFirstSentence = false;
        }
    }

    @Override
    public void leaveJavadocToken(DetailNode ast) {
        if (ast.getType() == JavadocCommentsTokenTypes.JAVADOC_CONTENT) {
            if (shouldValidateFirstSentence && !startsWithInheritDoc(ast)) {
                validateUntaggedSummary(ast);
            }
            shouldValidateFirstSentence = true;
        }
    }

    /**
     * Setter to control when to print violations if the Javadoc being examined by this check
     * violates the tight html rules defined at
     * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">
     *     Tight-HTML Rules</a>.
     *
     * @param shouldReportViolation value to which the field shall be set to
     * @since 8.3
     * @propertySince 13.7.0
     */
    @Override
    public void setViolateExecutionOnNonTightHtml(boolean shouldReportViolation) {
        super.setViolateExecutionOnNonTightHtml(shouldReportViolation);
    }

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

    @Override
    public void visitToken(DetailAST ast) {
        if (shouldCheck(ast)) {
            final DetailAST blockCommentNode = JavadocUtil.getAttachedJavadocComment(ast);
            if (blockCommentNode != null) {
                if (!JavadocTagInfo.INHERIT_DOC.isValidOn(ast)) {
                    inheritDocIsValid = false;
                }
                super.visitToken(blockCommentNode);
                inheritDocIsValid = true;
            }
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
            check = ScopeUtil.getSurroundingScope(ast)
                    .map(surroundingScope -> {
                        return customScope.isIn(scope)
                                && surroundingScope.isIn(scope)
                                && (excludeScope == null || !customScope.isIn(excludeScope)
                                        || !surroundingScope.isIn(excludeScope));
                    })
                    .orElse(Boolean.FALSE);
        }
        return check;
    }

    /**
     * Checks if the node starts with an {&#64;inheritDoc}.
     *
     * @param root the root node to examine.
     * @return {@code true} if the javadoc starts with an {&#64;inheritDoc}.
     */
    private boolean startsWithInheritDoc(DetailNode root) {
        boolean found = false;
        if (inheritDocIsValid) {
            DetailNode node = root.getFirstChild();

            while (node != null) {
                if (node.getType() == JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG
                        && node.getFirstChild().getType()
                                == JavadocCommentsTokenTypes.INHERIT_DOC_INLINE_TAG) {
                    found = true;
                }
                if ((node.getType() == JavadocCommentsTokenTypes.TEXT
                        || node.getType() == JavadocCommentsTokenTypes.HTML_ELEMENT)
                        && !CommonUtil.isBlank(node.getText())) {
                    break;
                }
                node = node.getNextSibling();
            }
        }
        return found;
    }

    /**
     * Checks the javadoc text for {@code period} at end and forbidden fragments.
     *
     * @param ast the javadoc text node
     */
    private void validateUntaggedSummary(DetailNode ast) {
        final String summaryDoc = getSummarySentence(ast);
        if (checkEmptyJavadoc && summaryDoc.isEmpty()) {
            log(ast.getLineNumber(), MSG_EMPTY);
        }
        if (checkFirstSentence && !summaryDoc.isEmpty()
                && !endOfSentenceFormat.matcher(summaryDoc).find()) {
            log(ast.getLineNumber(), MSG_NO_PERIOD);
        }
    }

    /**
     * Finds and returns summary sentence.
     *
     * @param ast javadoc root node.
     * @return violation string.
     */
    private static String getSummarySentence(DetailNode ast) {
        final StringBuilder result = new StringBuilder(256);
        DetailNode node = ast.getFirstChild();
        while (node != null) {
            if (node.getType() == JavadocCommentsTokenTypes.TEXT) {
                result.append(node.getText());
            }
            else {
                final String summary = result.toString();
                if (CommonUtil.isBlank(summary)
                        && node.getType() == JavadocCommentsTokenTypes.HTML_ELEMENT) {
                    final DetailNode htmlContentToken = JavadocUtil.findFirstToken(
                            node, JavadocCommentsTokenTypes.HTML_CONTENT);
                    result.append(getStringInsideHtmlTag(summary, htmlContentToken));
                }
            }
            node = node.getNextSibling();
        }
        String summary = result.toString().trim();
        if (summary.endsWith("*")) {
            summary = summary.substring(0, summary.length() - 1).trim();
        }
        return summary;
    }

    /**
     * Get concatenated string within text of html tags.
     *
     * @param result javadoc string
     * @param detailNode htmlContent node
     * @return java doc tag content appended in result
     */
    private static String getStringInsideHtmlTag(String result, DetailNode detailNode) {
        final StringBuilder contents = new StringBuilder(result);
        if (detailNode != null) {
            DetailNode tempNode = detailNode.getFirstChild();
            while (tempNode != null) {
                if (tempNode.getType() == JavadocCommentsTokenTypes.TEXT) {
                    contents.append(tempNode.getText());
                }
                tempNode = tempNode.getNextSibling();
            }
        }
        return contents.toString();
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
     * Setter to control whether to check if the Javadoc is missing a describing text.
     *
     * @param flag {@code true} if empty Javadoc checking should be done.
     * @since 3.4
     */
    public void setCheckEmptyJavadoc(boolean flag) {
        checkEmptyJavadoc = flag;
    }

}
