////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Contains utility methods for the checks.
 *
 * @author Oliver Burn
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 */
public final class CheckUtils
{
    // TODO: Make this a regex?
    // private static final String EQUALS_METHOD_NAME = "equals";

    /** prevent instances */
    private CheckUtils()
    {
        throw new UnsupportedOperationException();
    }

//    public static FullIdent createFullType(DetailAST typeAST) {
//        DetailAST arrayDeclAST =
//            typeAST.findFirstToken(TokenTypes.ARRAY_DECLARATOR);
//
//        return createFullTypeNoArrays(
//            arrayDeclAST == null ? typeAST : arrayDeclAST);
//    }
//
//    public static boolean isEqualsMethod(DetailAST detailAST) {
//        return detailAST.findFirstToken(TokenTypes.IDENT).getText().equals(
//            EQUALS_METHOD_NAME);
//    }
//
//    public static boolean isFinal(DetailAST detailAST) {
//        DetailAST modifiersAST =
    //detailAST.findFirstToken(TokenTypes.MODIFIERS);
//
//        return modifiersAST.findFirstToken(TokenTypes.FINAL) != null;
//    }
//
//    public static boolean isInObjBlock(DetailAST detailAST) {
//        return detailAST.getParent().getType() == TokenTypes.OBJBLOCK;
//    }
//
//    public static String getIdentText(DetailAST detailAST) {
//        return detailAST.findFirstToken(TokenTypes.IDENT).getText();
//    }
//
//    private static FullIdent createFullTypeNoArrays(DetailAST typeAST) {
//        return FullIdent.createFullIdent((DetailAST) typeAST.getFirstChild());
//    }


    /**
     * Returns whether a token represents an ELSE as part of an ELSE / IF set.
     * @param aAST the token to check
     * @return whether it is
     */
    public static boolean isElseIf(DetailAST aAST)
    {
        final DetailAST parentAST = aAST.getParent();

        return (aAST.getType() == TokenTypes.LITERAL_IF)
            && (isElse(parentAST) || isElseWithCurlyBraces(parentAST));
    }

    /**
     * Returns whether a token represents an ELSE.
     * @param aAST the token to check
     * @return whether the token represents an ELSE
     */
    private static boolean isElse(DetailAST aAST)
    {
        return aAST.getType() == TokenTypes.LITERAL_ELSE;
    }

    /**
     * Returns whether a token represents an SLIST as part of an ELSE
     * statement.
     * @param aAST the token to check
     * @return whether the toke does represent an SLIST as part of an ELSE
     */
    private static boolean isElseWithCurlyBraces(DetailAST aAST)
    {
        return (aAST.getType() == TokenTypes.SLIST)
            && (aAST.getChildCount() == 2)
            && isElse(aAST.getParent());
    }

    /**
     * Creates <code>FullIdent</code> for given type node.
     * @param aTypeAST a type node.
     * @return <code>FullIdent</code> for given type.
     */
    public static FullIdent createFullType(DetailAST aTypeAST)
    {
        DetailAST arrayDeclAST =
            aTypeAST.findFirstToken(TokenTypes.ARRAY_DECLARATOR);

        return createFullTypeNoArrays(arrayDeclAST == null ? aTypeAST
                                                           : arrayDeclAST);
    }

    /**
     * @param aTypeAST a type node (no array)
     * @return <code>FullIdent</code> for given type.
     */
    private static FullIdent createFullTypeNoArrays(DetailAST aTypeAST)
    {
        return FullIdent.createFullIdent((DetailAST) aTypeAST.getFirstChild());
    }
}
