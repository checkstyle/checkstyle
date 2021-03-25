////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**

 */
@StatelessCheck
public class JavadocParamOrder extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_JAVADOC_TAG_ORDER = "javadoc.tag.order";

    /** Compiled regexp to match Javadoc tags that take an argument. */
    private static final Pattern MATCH_JAVADOC_ARG = CommonUtil.createPattern(
            "^\\s*(?>\\*|\\/\\*\\*)?\\s*@(throws|exception|param)\\s+(\\S+)\\s+\\S*");
    /** Compiled regexp to match Javadoc tags with argument but with missing description. */
    private static final Pattern MATCH_JAVADOC_ARG_MISSING_DESCRIPTION =
        CommonUtil.createPattern("^\\s*(?>\\*|\\/\\*\\*)?\\s*@(throws|exception|param)\\s+"
            + "(\\S[^*]*)(?:(\\s+|\\*\\/))?");

    @Override
    public final int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final List<DetailAST> methodParams = getParameters(ast);
        final List<DetailAST> methodTypeParams = CheckUtil.getTypeParameters(ast);

        final FileContents contents = getFileContents();
        final int lineNo = ast.getLineNo();
        final TextBlock textBlock = contents.getJavadocBefore(lineNo);

        if (textBlock != null) {
            final List<JavadocTag> javadocParams = getMethodTags(textBlock);

            // We will check order separately for methodParams and methodTypeParams
            final List<JavadocTag> javadocParam = checkMethodTypeParamsOrder(methodTypeParams, javadocParams);
            checkMethodParamsOrder(methodParams, javadocParam);
        }
    }

    /**
     * Checks order of @param tags for matching method type parameters only.
     *
     * @param methodTypeParams list of method type parameters
     * @param javadocParams list of javadoc parameters
     * @return list of javadoc parameters
     */
    private List<JavadocTag> checkMethodTypeParamsOrder(final List<DetailAST> methodTypeParams,
                                            final List<JavadocTag> javadocParams) {

        final ListIterator<JavadocTag> javadocTagIt = javadocParams.listIterator();
        final ListIterator<DetailAST> typeParamsIt = methodTypeParams.listIterator();

        while (typeParamsIt.hasNext() && javadocTagIt.hasNext()) {
            final JavadocTag tag = javadocTagIt.next();
            if (!tag.isParamTag()) {
                continue;
            }

            javadocTagIt.remove();

            final DetailAST typeParam = typeParamsIt.next();
            final String arg1 = tag.getFirstArg();
            if (!typeParam.findFirstToken(TokenTypes.IDENT).getText()
                    .equals(arg1.substring(1, arg1.length() - 1))) {
                log(tag.getLineNo(), tag.getColumnNo(), MSG_JAVADOC_TAG_ORDER, "@param");
            }

        }
        return javadocParams;
    }

    /**
     * Checks order of @param tags for matching method parameters.
     *
     * @param methodParams list of method parameters
     * @param javadocParams list of javadoc parameters
     */
    private void checkMethodParamsOrder(final List<DetailAST> methodParams,
                                        final List<JavadocTag> javadocParams) {

        final ListIterator<JavadocTag> javadocTag = javadocParams.listIterator();
        final ListIterator<DetailAST> methodTag = methodParams.listIterator();
        while (javadocTag.hasNext() && methodTag.hasNext()) {
            final JavadocTag tag = javadocTag.next();
            if (!tag.isParamTag()) {
                continue;
            }
            final DetailAST mTag = methodTag.next();
            final String arg1 = tag.getFirstArg();
            final String arg2 = mTag.getText();

            if(!arg1.equals(arg2)) {
                log(tag.getLineNo(), tag.getColumnNo(), MSG_JAVADOC_TAG_ORDER,"@param");
            }
        }
    }

    /**
     * Computes the parameter nodes for a method.
     *
     * @param ast the method node.
     * @return the list of parameter nodes for ast.
     */
    private static List<DetailAST> getParameters(DetailAST ast) {
        final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
        final List<DetailAST> returnValue = new ArrayList<>();
        if(params != null) {
            DetailAST child = params.getFirstChild();
            while (child != null) {
            if (child.getType() == TokenTypes.PARAMETER_DEF) {
                final DetailAST ident = child.findFirstToken(TokenTypes.IDENT);
                if (ident != null) {
                    returnValue.add(ident);
                }
            }
            child = child.getNextSibling();
            }
        }

        return returnValue;
    }

    /**
     * Returns the tags in a javadoc comment. Only finds throws, exception,
     * param, return and see tags.
     *
     * @param comment the Javadoc comment
     * @return the tags found
     */
    private static List<JavadocTag> getMethodTags(TextBlock comment) {
        final String[] lines = comment.getText();
        final List<JavadocTag> tags = new ArrayList<>();
        int currentLine = comment.getStartLineNo() - 1;
        final int startColumnNumber = comment.getStartColNo();

        for (int i = 0; i < lines.length; i++) {
            currentLine++;
            final Matcher javadocArgMatcher =
                MATCH_JAVADOC_ARG.matcher(lines[i]);
            final Matcher javadocArgMissingDescriptionMatcher =
                MATCH_JAVADOC_ARG_MISSING_DESCRIPTION.matcher(lines[i]);

            if (javadocArgMatcher.find()) {
                final int col = calculateTagColumn(javadocArgMatcher, i, startColumnNumber);
                tags.add(new JavadocTag(currentLine, col, javadocArgMatcher.group(1),
                        javadocArgMatcher.group(2)));
            }
            else if (javadocArgMissingDescriptionMatcher.find()) {
                final int col = calculateTagColumn(javadocArgMissingDescriptionMatcher, i,
                    startColumnNumber);
                tags.add(new JavadocTag(currentLine, col,
                    javadocArgMissingDescriptionMatcher.group(1),
                    javadocArgMissingDescriptionMatcher.group(2)));
            }
        }
        return tags;
    }

    /**
     * Calculates column number using Javadoc tag matcher.
     *
     * @param javadocTagMatcher found javadoc tag matcher
     * @param lineNumber line number of Javadoc tag in comment
     * @param startColumnNumber column number of Javadoc comment beginning
     * @return column number
     */
    private static int calculateTagColumn(Matcher javadocTagMatcher,
            int lineNumber, int startColumnNumber) {
        int col = javadocTagMatcher.start(1) - 1;
        if (lineNumber == 0) {
            col += startColumnNumber;
        }
        return col;
    }
}


