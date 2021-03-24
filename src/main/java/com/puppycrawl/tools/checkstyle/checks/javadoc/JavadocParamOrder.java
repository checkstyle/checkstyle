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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**

 */
@StatelessCheck
public class JavadocParamOrder extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_JAVADOC_TAG_ORDER = "javadoc.tag.order";

    @Override
    public final int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
        };
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

        final List<JavadocTag> javadocParams = getJavadocTags(textBlock);

        // We will check order separately for methodParams and methodTypeParams
        final List<JavadocTag> javadocParam = method1(methodTypeParams, javadocParams);
        method2(methodParams, javadocParam);
    }

    /**
     * xyz.
     *
     * @param methodTypeParams list of method type parameters
     * @param javadocParams list of javadoc parameters
     */
    private List<JavadocTag> method1(final List<DetailAST> methodTypeParams,
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
            //final String arg2 = typeParamsIt.getText();
            if (CommonUtil.startsWithChar(arg1, '<') && CommonUtil.endsWithChar(arg1, '>')) {
                if (typeParam.findFirstToken(TokenTypes.IDENT).getText()
                    .equals(arg1.substring(1, arg1.length() - 1))) {
                    log(tag.getLineNo(), tag.getColumnNo(), MSG_JAVADOC_TAG_ORDER, "@param");
                }
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
    private void method2(final List<DetailAST> methodParams, final List<JavadocTag> javadocParams) {

        final ListIterator<JavadocTag> javadocTag = javadocParams.listIterator();
        final ListIterator<DetailAST> methodTag = methodParams.listIterator();
        while (javadocTag.hasNext()) {
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
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
     * Gets all standalone tags from a given javadoc.
     *
     * @param textBlock the Javadoc comment to process.
     * @return all standalone tags from the given javadoc.
     */
    private static List<JavadocTag> getJavadocTags(TextBlock textBlock) {
        final JavadocTags tags = JavadocUtil.getJavadocTags(textBlock,
            JavadocUtil.JavadocTagType.BLOCK);

        return tags.getValidTags();
    }
}


