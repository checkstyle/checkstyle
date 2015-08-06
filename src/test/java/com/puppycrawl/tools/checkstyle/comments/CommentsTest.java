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

package com.puppycrawl.tools.checkstyle.comments;

import static com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATIONS;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.BLOCK_COMMENT_BEGIN;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.BLOCK_COMMENT_END;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.COMMENT_CONTENT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.DOT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.EXPR;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.LCURLY;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_CLASS;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_NULL;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_PROTECTED;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_PUBLIC;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_RETURN;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.LPAREN;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.MODIFIERS;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.OBJBLOCK;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.PACKAGE_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.PARAMETERS;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.RPAREN;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.SEMI;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.SINGLE_LINE_COMMENT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Comment;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

public class CommentsTest extends BaseCheckTestSupport {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /*
     * +--CLASS_DEF [1,0]
     *     |
     *     +--MODIFIERS [1,0]
     *         |
     *         +--LITERAL_PUBLIC [1,0]
     *     +--LITERAL_CLASS [1,7]
     *     +--BLOCK_COMMENT_BEGIN [1,13]
     *         |
     *         +--COMMENT_CONTENT [1,15]
     *         +--BLOCK_COMMENT_END [3,4]
     *     +--IDENT [4,0]
     *     +--OBJBLOCK [5,0]
     *         |
     *         +--LCURLY [5,0]
     *             |
     *             +--SINGLE_LINE_COMMENT [5,2]
     *                 |
     *                 +--COMMENT_CONTENT [5,4]
     *         +--RCURLY [6,0]
     */
    private static DetailAST buildInput_1() {
        DetailAST packageDef = new DetailAST();
        packageDef.setType(PACKAGE_DEF);
        packageDef.setText("package");
        packageDef.setLineNo(1);
        packageDef.setColumnNo(0);

        DetailAST annotationsDef = new DetailAST();
        annotationsDef.setType(ANNOTATIONS);
        annotationsDef.setText("ANNOTATIONS");
        annotationsDef.setLineNo(1);
        annotationsDef.setColumnNo(39);

        DetailAST dotDef = new DetailAST();
        dotDef.setType(DOT);
        dotDef.setText(".");
        dotDef.setLineNo(1);
        dotDef.setColumnNo(39);

        DetailAST subDotDef = new DetailAST();
        subDotDef.setType(DOT);
        subDotDef.setText(".");
        subDotDef.setLineNo(1);
        subDotDef.setColumnNo(28);

        DetailAST subDotDef1 = new DetailAST();
        subDotDef1.setType(DOT);
        subDotDef1.setText(".");
        subDotDef1.setLineNo(1);
        subDotDef1.setColumnNo(22);

        DetailAST subDotDef2 = new DetailAST();
        subDotDef2.setType(DOT);
        subDotDef2.setText(".");
        subDotDef2.setLineNo(1);
        subDotDef2.setColumnNo(11);

        DetailAST identDef = new DetailAST();
        identDef.setType(IDENT);
        identDef.setText("com");
        identDef.setLineNo(1);
        identDef.setColumnNo(8);

        DetailAST identDef2 = new DetailAST();
        identDef2.setType(IDENT);
        identDef2.setText("puppycrawl");
        identDef2.setLineNo(1);
        identDef2.setColumnNo(12);

        DetailAST identDef3 = new DetailAST();
        identDef3.setType(IDENT);
        identDef3.setText("tools");
        identDef3.setLineNo(1);
        identDef3.setColumnNo(23);

        DetailAST semiDef = new DetailAST();
        semiDef.setType(SEMI);
        semiDef.setText(";");
        semiDef.setLineNo(1);
        semiDef.setColumnNo(48);

        DetailAST identDef4 = new DetailAST();
        identDef4.setType(IDENT);
        identDef4.setText("checkstyle");
        identDef4.setLineNo(1);
        identDef4.setColumnNo(29);

        DetailAST identDef5 = new DetailAST();
        identDef5.setType(IDENT);
        identDef5.setText("comments");
        identDef5.setLineNo(1);
        identDef5.setColumnNo(40);

        DetailAST classDef = new DetailAST();
        classDef.setType(CLASS_DEF);
        classDef.setText("CLASS_DEF");
        classDef.setLineNo(2);
        classDef.setColumnNo(0);

        DetailAST modifiers = new DetailAST();
        modifiers.setType(MODIFIERS);
        modifiers.setText("MODIFIERS");
        modifiers.setLineNo(2);
        modifiers.setColumnNo(0);

        DetailAST literalPublic = new DetailAST();
        literalPublic.setType(LITERAL_PUBLIC);
        literalPublic.setText("public");
        literalPublic.setLineNo(2);
        literalPublic.setColumnNo(0);

        DetailAST literalClass = new DetailAST();
        literalClass.setType(LITERAL_CLASS);
        literalClass.setText("class");
        literalClass.setLineNo(2);
        literalClass.setColumnNo(7);

        DetailAST blockCommentStart = new DetailAST();
        blockCommentStart.setType(BLOCK_COMMENT_BEGIN);
        blockCommentStart.setText("/*");
        blockCommentStart.setLineNo(2);
        blockCommentStart.setColumnNo(13);

        DetailAST blockCommentContent = new DetailAST();
        blockCommentContent.setType(COMMENT_CONTENT);
        blockCommentContent.setText(LINE_SEPARATOR + "    i'mcomment567" + LINE_SEPARATOR + "    ");
        blockCommentContent.setLineNo(2);
        blockCommentContent.setColumnNo(15);

        DetailAST blockCommentEnd = new DetailAST();
        blockCommentEnd.setType(BLOCK_COMMENT_END);
        blockCommentEnd.setText("*/");
        blockCommentEnd.setLineNo(4);
        blockCommentEnd.setColumnNo(4);

        DetailAST ident = new DetailAST();
        ident.setType(IDENT);
        ident.setText("InputCommentsTest_1");
        ident.setLineNo(5);
        ident.setColumnNo(0);

        DetailAST objBlock = new DetailAST();
        objBlock.setType(OBJBLOCK);
        objBlock.setText("OBJBLOCK");
        objBlock.setLineNo(6);
        objBlock.setColumnNo(0);

        DetailAST lcurly = new DetailAST();
        lcurly.setType(LCURLY);
        lcurly.setText("{");
        lcurly.setLineNo(6);
        lcurly.setColumnNo(0);

        DetailAST slComment = new DetailAST();
        slComment.setType(SINGLE_LINE_COMMENT);
        slComment.setText("//");
        slComment.setLineNo(6);
        slComment.setColumnNo(2);

        DetailAST slCommentContent = new DetailAST();
        slCommentContent.setType(COMMENT_CONTENT);
        slCommentContent.setText(" comment to left curly brace" + LINE_SEPARATOR);
        slCommentContent.setLineNo(6);
        slCommentContent.setColumnNo(4);

        DetailAST rcurly = new DetailAST();
        rcurly.setType(RCURLY);
        rcurly.setText("}");
        rcurly.setLineNo(7);
        rcurly.setColumnNo(0);

        packageDef.setFirstChild(annotationsDef);
        annotationsDef.setNextSibling(dotDef);
        dotDef.setFirstChild(subDotDef);
        subDotDef.setFirstChild(subDotDef1);
        subDotDef1.setFirstChild(subDotDef2);
        subDotDef2.setFirstChild(identDef);
        identDef.setNextSibling(identDef2);
        subDotDef2.setNextSibling(identDef3);
        subDotDef1.setNextSibling(identDef4);
        subDotDef.setNextSibling(identDef5);
        dotDef.setNextSibling(semiDef);
        packageDef.setNextSibling(classDef);
        classDef.setFirstChild(modifiers);
        modifiers.setNextSibling(literalClass);
        literalClass.setNextSibling(blockCommentStart);
        blockCommentStart.setNextSibling(ident);
        ident.setNextSibling(objBlock);

        modifiers.setFirstChild(literalPublic);

        blockCommentStart.setFirstChild(blockCommentContent);
        blockCommentContent.setNextSibling(blockCommentEnd);

        objBlock.setFirstChild(lcurly);
        lcurly.setNextSibling(slComment);
        slComment.setNextSibling(rcurly);

        slComment.setFirstChild(slCommentContent);

        return packageDef;
    }

    /*
     * +--CLASS_DEF [2,0]
     *     |
     *     +--MODIFIERS [2,0]
     *     +--SINGLE_LINE_COMMENT [1,0]
     *         |
     *         +--COMMENT_CONTENT [1,2]
     *     +--LITERAL_CLASS [2,0]
     *     +--IDENT [2,6]
     *     +--OBJBLOCK [3,0]
     *         |
     *         +--LCURLY [3,0]
     *         +--METHOD_DEF [9,4]
     *                 |
     *                 +--MODIFIERS [9,4]
     *                     |
     *                     +--BLOCK_COMMENT_BEGIN [4,4]
     *                         |
     *                         +--COMMENT_CONTENT [4,6]
     *                         +--BLOCK_COMMENT_END [8,5]
     *                     +--LITERAL_PROTECTED [9,4]
     *                 +--TYPE [9,14]
     *                     |
     *                     +--IDENT [9,14]
     *                 +--IDENT [9,21]
     *                 +--LPAREN [9,25]
     *                 +--PARAMETERS [9,26]
     *                 +--RPAREN [9,26]
     *                 +--SLIST [10,4]
     *                     |
     *                     +--RCURLY [11,4]
     *         +--RCURLY [12,0]
     */
    private static DetailAST buildInput_2() {
        DetailAST packageDef = new DetailAST();
        packageDef.setType(PACKAGE_DEF);
        packageDef.setText("package");
        packageDef.setLineNo(1);
        packageDef.setColumnNo(0);

        DetailAST annotationsDef = new DetailAST();
        annotationsDef.setType(ANNOTATIONS);
        annotationsDef.setText("ANNOTATIONS");
        annotationsDef.setLineNo(1);
        annotationsDef.setColumnNo(39);

        DetailAST dotDef = new DetailAST();
        dotDef.setType(DOT);
        dotDef.setText(".");
        dotDef.setLineNo(1);
        dotDef.setColumnNo(39);

        DetailAST subDotDef = new DetailAST();
        subDotDef.setType(DOT);
        subDotDef.setText(".");
        subDotDef.setLineNo(1);
        subDotDef.setColumnNo(28);

        DetailAST subDotDef1 = new DetailAST();
        subDotDef1.setType(DOT);
        subDotDef1.setText(".");
        subDotDef1.setLineNo(1);
        subDotDef1.setColumnNo(22);

        DetailAST subDotDef2 = new DetailAST();
        subDotDef2.setType(DOT);
        subDotDef2.setText(".");
        subDotDef2.setLineNo(1);
        subDotDef2.setColumnNo(11);

        DetailAST identDef = new DetailAST();
        identDef.setType(IDENT);
        identDef.setText("com");
        identDef.setLineNo(1);
        identDef.setColumnNo(8);

        DetailAST identDef2 = new DetailAST();
        identDef2.setType(IDENT);
        identDef2.setText("puppycrawl");
        identDef2.setLineNo(1);
        identDef2.setColumnNo(12);

        DetailAST identDef3 = new DetailAST();
        identDef3.setType(IDENT);
        identDef3.setText("tools");
        identDef3.setLineNo(1);
        identDef3.setColumnNo(23);

        DetailAST semiDef = new DetailAST();
        semiDef.setType(SEMI);
        semiDef.setText(";");
        semiDef.setLineNo(1);
        semiDef.setColumnNo(48);

        DetailAST identDef4 = new DetailAST();
        identDef4.setType(IDENT);
        identDef4.setText("checkstyle");
        identDef4.setLineNo(1);
        identDef4.setColumnNo(29);

        DetailAST identDef5 = new DetailAST();
        identDef5.setType(IDENT);
        identDef5.setText("comments");
        identDef5.setLineNo(1);
        identDef5.setColumnNo(40);

        DetailAST classDef = new DetailAST();
        classDef.setType(CLASS_DEF);
        classDef.setText("CLASS_DEF");
        classDef.setLineNo(3);
        classDef.setColumnNo(0);

        DetailAST modifiers = new DetailAST();
        modifiers.setType(MODIFIERS);
        modifiers.setText("MODIFIERS");
        modifiers.setLineNo(3);
        modifiers.setColumnNo(0);

        classDef.setFirstChild(modifiers);

        DetailAST slComment = new DetailAST();
        slComment.setType(SINGLE_LINE_COMMENT);
        slComment.setText("//");
        slComment.setLineNo(2);
        slComment.setColumnNo(0);

        DetailAST slCommentContent = new DetailAST();
        slCommentContent.setType(COMMENT_CONTENT);
        slCommentContent.setText(" my class" + LINE_SEPARATOR);
        slCommentContent.setLineNo(2);
        slCommentContent.setColumnNo(2);

        slComment.setFirstChild(slCommentContent);
        modifiers.setNextSibling(slComment);

        DetailAST literalClass = new DetailAST();
        literalClass.setType(LITERAL_CLASS);
        literalClass.setText("class");
        literalClass.setLineNo(3);
        literalClass.setColumnNo(0);

        slComment.setNextSibling(literalClass);

        DetailAST identClassName = new DetailAST();
        identClassName.setType(IDENT);
        identClassName.setText("InputCommentsTest_2");
        identClassName.setLineNo(3);
        identClassName.setColumnNo(6);

        literalClass.setNextSibling(identClassName);

        DetailAST objBlock = new DetailAST();
        objBlock.setType(OBJBLOCK);
        objBlock.setText("OBJBLOCK");
        objBlock.setLineNo(4);
        objBlock.setColumnNo(0);

        identClassName.setNextSibling(objBlock);

        DetailAST lcurly = new DetailAST();
        lcurly.setType(LCURLY);
        lcurly.setText("{");
        lcurly.setLineNo(4);
        lcurly.setColumnNo(0);

        objBlock.setFirstChild(lcurly);

        DetailAST methodDef = new DetailAST();
        methodDef.setType(METHOD_DEF);
        methodDef.setText("METHOD_DEF");
        methodDef.setLineNo(10);
        methodDef.setColumnNo(4);

        lcurly.setNextSibling(methodDef);

        DetailAST rcurly = new DetailAST();
        rcurly.setType(RCURLY);
        rcurly.setText("}");
        rcurly.setLineNo(14);
        rcurly.setColumnNo(0);

        methodDef.setNextSibling(rcurly);

        DetailAST methodModifiers = new DetailAST();
        methodModifiers.setType(MODIFIERS);
        methodModifiers.setText("MODIFIERS");
        methodModifiers.setLineNo(10);
        methodModifiers.setColumnNo(4);

        methodDef.setFirstChild(methodModifiers);

        DetailAST methodType = new DetailAST();
        methodType.setType(TYPE);
        methodType.setText("TYPE");
        methodType.setLineNo(10);
        methodType.setColumnNo(14);

        methodModifiers.setNextSibling(methodType);

        DetailAST identMethodType = new DetailAST();
        identMethodType.setType(IDENT);
        identMethodType.setText("String");
        identMethodType.setLineNo(10);
        identMethodType.setColumnNo(14);

        methodType.setFirstChild(identMethodType);

        DetailAST identMethodName = new DetailAST();
        identMethodName.setType(IDENT);
        identMethodName.setText("line");
        identMethodName.setLineNo(10);
        identMethodName.setColumnNo(21);

        methodType.setNextSibling(identMethodName);

        DetailAST lparen = new DetailAST();
        lparen.setType(LPAREN);
        lparen.setText("(");
        lparen.setLineNo(10);
        lparen.setColumnNo(25);

        identMethodName.setNextSibling(lparen);

        DetailAST parameters = new DetailAST();
        parameters.setType(PARAMETERS);
        parameters.setText("PARAMETERS");
        parameters.setLineNo(10);
        parameters.setColumnNo(26);

        lparen.setNextSibling(parameters);

        DetailAST returnDef = new DetailAST();
        returnDef.setType(LITERAL_RETURN);
        returnDef.setText("return");
        returnDef.setLineNo(12);
        returnDef.setColumnNo(2);

        DetailAST exprDef = new DetailAST();
        exprDef.setType(EXPR);
        exprDef.setText("EXPR");
        exprDef.setLineNo(12);
        exprDef.setColumnNo(9);

        DetailAST nullDef = new DetailAST();
        nullDef.setType(LITERAL_NULL);
        nullDef.setText("null");
        nullDef.setLineNo(12);
        nullDef.setColumnNo(9);

        DetailAST rparen = new DetailAST();
        rparen.setType(RPAREN);
        rparen.setText(")");
        rparen.setLineNo(10);
        rparen.setColumnNo(26);

        parameters.setNextSibling(rparen);

        DetailAST slist = new DetailAST();
        slist.setType(SLIST);
        slist.setText("{");
        slist.setLineNo(11);
        slist.setColumnNo(4);

        DetailAST semiDef1 = new DetailAST();
        semiDef1.setType(SEMI);
        semiDef1.setText(";");
        semiDef1.setLineNo(12);
        semiDef1.setColumnNo(13);

        rparen.setNextSibling(slist);

        DetailAST methodRcurly = new DetailAST();
        methodRcurly.setType(RCURLY);
        methodRcurly.setText("}");
        methodRcurly.setLineNo(13);
        methodRcurly.setColumnNo(4);

        slist.setFirstChild(returnDef);
        returnDef.setNextSibling(methodRcurly);
        returnDef.setFirstChild(exprDef);
        exprDef.setFirstChild(nullDef);
        exprDef.setNextSibling(semiDef1);

        DetailAST blockCommentStart = new DetailAST();
        blockCommentStart.setType(BLOCK_COMMENT_BEGIN);
        blockCommentStart.setText("/*");
        blockCommentStart.setLineNo(5);
        blockCommentStart.setColumnNo(4);

        DetailAST blockCommentContent = new DetailAST();
        blockCommentContent.setType(COMMENT_CONTENT);
        blockCommentContent.setText("*" + LINE_SEPARATOR
                + "     * Lines <b>method</b>." + LINE_SEPARATOR
                + "     * " + LINE_SEPARATOR
                + "     * @return string." + LINE_SEPARATOR
                + "     ");
        blockCommentContent.setLineNo(5);
        blockCommentContent.setColumnNo(6);

        blockCommentStart.setFirstChild(blockCommentContent);

        DetailAST blockCommentEnd = new DetailAST();
        blockCommentEnd.setType(BLOCK_COMMENT_END);
        blockCommentEnd.setText("*/");
        blockCommentEnd.setLineNo(9);
        blockCommentEnd.setColumnNo(5);

        blockCommentContent.setNextSibling(blockCommentEnd);
        methodModifiers.setFirstChild(blockCommentStart);

        DetailAST literalProtected = new DetailAST();
        literalProtected.setType(LITERAL_PROTECTED);
        literalProtected.setText("protected");
        literalProtected.setLineNo(10);
        literalProtected.setColumnNo(4);
        packageDef.setFirstChild(annotationsDef);
        annotationsDef.setNextSibling(dotDef);
        dotDef.setFirstChild(subDotDef);
        subDotDef.setFirstChild(subDotDef1);
        subDotDef1.setFirstChild(subDotDef2);
        subDotDef2.setFirstChild(identDef);
        identDef.setNextSibling(identDef2);
        subDotDef2.setNextSibling(identDef3);
        subDotDef1.setNextSibling(identDef4);
        subDotDef.setNextSibling(identDef5);
        dotDef.setNextSibling(semiDef);
        packageDef.setNextSibling(classDef);
        blockCommentStart.setNextSibling(literalProtected);

        return packageDef;
    }

    @Test
    public void testCompareExpectedTreeWithInput_1() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(CompareTreesWithComments.class);
        CompareTreesWithComments.expectedTree = buildInput_1();
        final String[] expected = {};
        verify(checkConfig, getPath("comments" + File.separator
                + "InputCommentsTest_1.java"), expected);
    }

    @Test
    public void testCompareExpectedTreeWithInput_2() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(CompareTreesWithComments.class);
        CompareTreesWithComments.expectedTree = buildInput_2();
        final String[] expected = {};
        verify(checkConfig, getPath("comments" + File.separator
                + "InputCommentsTest_2.java"), expected);
    }

    @Test
    public void testToString() throws Exception {
        Comment comment = new Comment(new String[]{"value"}, 1, 2, 3);
        Assert.assertEquals("Comment[2:1-2:3]", comment.toString());
    }
}
