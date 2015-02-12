////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.modifier;

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks for redundant modifiers in interface and annotation definitions.
 * Also checks for redundant final modifiers on methods of final classes.
 *
 * @author lkuehne
 */
public class RedundantModifierCheck
    extends Check
{
    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.INTERFACE_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens()
    {
        return new int[] {};
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.INTERFACE_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast)
    {
        if (TokenTypes.INTERFACE_DEF == ast.getType()) {
            final DetailAST modifiers =
                ast.findFirstToken(TokenTypes.MODIFIERS);
            if (null != modifiers) {
                for (final int tokenType : new int[] {
                    TokenTypes.LITERAL_STATIC,
                    TokenTypes.ABSTRACT, })
                {
                    final DetailAST modifier =
                            modifiers.findFirstToken(tokenType);
                    if (null != modifier) {
                        log(modifier.getLineNo(), modifier.getColumnNo(),
                                "redundantModifier", modifier.getText());
                    }
                }
            }
        }
        else if (isInterfaceOrAnnotationMember(ast)) {
            final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
            DetailAST modifier = modifiers.getFirstChild();
            while (modifier != null) {

                // javac does not allow final or static in interface methods
                // order annotation fields hence no need to check that this
                // is not a method or annotation field

                final int type = modifier.getType();
                if ((type == TokenTypes.LITERAL_PUBLIC)
                    || ((type == TokenTypes.LITERAL_STATIC)
                            && ast.getType() != TokenTypes.METHOD_DEF)
                    || (type == TokenTypes.ABSTRACT)
                    || (type == TokenTypes.FINAL))
                {
                    log(modifier.getLineNo(), modifier.getColumnNo(),
                            "redundantModifier", modifier.getText());
                    break;
                }

                modifier = modifier.getNextSibling();
            }
        }
        else if (ast.getType() == TokenTypes.METHOD_DEF) {
            final DetailAST modifiers =
                            ast.findFirstToken(TokenTypes.MODIFIERS);
            // private method?
            boolean checkFinal =
                modifiers.branchContains(TokenTypes.LITERAL_PRIVATE);
            // declared in a final class?
            DetailAST parent = ast.getParent();
            while (parent != null) {
                if (parent.getType() == TokenTypes.CLASS_DEF) {
                    final DetailAST classModifiers =
                        parent.findFirstToken(TokenTypes.MODIFIERS);
                    checkFinal |=
                        classModifiers.branchContains(TokenTypes.FINAL);
                    break;
                }
                parent = parent.getParent();
            }
            if (checkFinal && !isAnnotatedWithSafeVarargs(ast)) {
                DetailAST modifier = modifiers.getFirstChild();
                while (modifier != null) {
                    final int type = modifier.getType();
                    if (type == TokenTypes.FINAL) {
                        log(modifier.getLineNo(), modifier.getColumnNo(),
                                "redundantModifier", modifier.getText());
                        break;
                    }
                    modifier = modifier.getNextSibling();
                }
            }
        }
    }

    /**
     * Checks if current AST node is member of Interface or Annotation, not of their subnodes.
     * @param ast AST node
     * @return true or false
     */
    private static boolean isInterfaceOrAnnotationMember(DetailAST ast)
    {
        final DetailAST parentTypeDef = ast.getParent().getParent();
        return parentTypeDef.getType() == TokenTypes.INTERFACE_DEF
               || parentTypeDef.getType() == TokenTypes.ANNOTATION_DEF;
    }

    /**
     * Checks if method definition is annotated with
     * <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/SafeVarargs.html">
     * SafeVarargs</a> annotation
     * @param methodDef method definition node
     * @return true or false
     */
    private static boolean isAnnotatedWithSafeVarargs(DetailAST methodDef)
    {
        boolean result = false;
        final List<DetailAST> methodAnnotationsList = getMethodAnnotationsList(methodDef);
        for (DetailAST annotationNode : methodAnnotationsList) {
            if ("SafeVarargs".equals(annotationNode.getLastChild().getText())) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Gets the list of annotations on method definition
     * @param methodDef method definition node
     * @return List of annotations
     */
    private static List<DetailAST> getMethodAnnotationsList(DetailAST methodDef)
    {
        final List<DetailAST> annotationsList = new ArrayList<>();
        final DetailAST modifiers = methodDef.findFirstToken(TokenTypes.MODIFIERS);
        DetailAST modifier = modifiers.getFirstChild();
        while (modifier != null) {
            if (modifier.getType() == TokenTypes.ANNOTATION) {
                annotationsList.add(modifier);
            }
            modifier = modifier.getNextSibling();
        }
        return annotationsList;
    }
}
