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
    public void visitToken(DetailAST aAST)
    {
        if (TokenTypes.INTERFACE_DEF == aAST.getType()) {
            final DetailAST modifiers =
                aAST.findFirstToken(TokenTypes.MODIFIERS);
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
        else if (isInterfaceOrAnnotationMember(aAST)) {
            final DetailAST modifiers = aAST.findFirstToken(TokenTypes.MODIFIERS);
            DetailAST modifier = modifiers.getFirstChild();
            while (modifier != null) {

                // javac does not allow final or static in interface methods
                // order annotation fields hence no need to check that this
                // is not a method or annotation field

                final int type = modifier.getType();
                if ((type == TokenTypes.LITERAL_PUBLIC)
                    || ((type == TokenTypes.LITERAL_STATIC)
                            && aAST.getType() != TokenTypes.METHOD_DEF)
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
        else if (aAST.getType() == TokenTypes.METHOD_DEF) {
            final DetailAST modifiers =
                            aAST.findFirstToken(TokenTypes.MODIFIERS);
            // private method?
            boolean checkFinal =
                modifiers.branchContains(TokenTypes.LITERAL_PRIVATE);
            // declared in a final class?
            DetailAST parent = aAST.getParent();
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
            if (checkFinal) {
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
     * @param aAST AST node
     * @return true or false
     */
    private static boolean isInterfaceOrAnnotationMember(DetailAST aAST)
    {
        final DetailAST parentTypeDef = aAST.getParent().getParent();
        return parentTypeDef.getType() == TokenTypes.INTERFACE_DEF
               || parentTypeDef.getType() == TokenTypes.ANNOTATION_DEF;
    }
}
