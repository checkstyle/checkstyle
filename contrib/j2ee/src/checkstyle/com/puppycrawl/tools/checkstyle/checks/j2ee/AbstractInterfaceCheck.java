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
package com.puppycrawl.tools.checkstyle.checks.j2ee;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks interface requirements.
 * @author Rick Giles
 */
public class AbstractInterfaceCheck
    extends Check
{
    /**
     * @see com.puppycrawl.tools.checkstyle.api.Check
     */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.INTERFACE_DEF};
    }

    /**
     * @see com.puppycrawl.tools.checkstyle.api.Check
     */
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    /**
     * Checks methods of a local home interface for required properties.
     * @param aAST the home interface AST to check.
     */
    protected void checkMethods(DetailAST aAST)
    {
        final DetailAST objBlock = aAST.findFirstToken(TokenTypes.OBJBLOCK);
        if (objBlock != null) {
            DetailAST child = (DetailAST) objBlock.getFirstChild();
            while (child != null) {
                if (child.getType() == TokenTypes.METHOD_DEF) {
                    checkMethod(child, "create", "javax.ejb.CreateException");
                    checkMethod(child, "find", "javax.ejb.FinderException");
                }
                child = (DetailAST) child.getNextSibling();
            }
        }
    }

    /**
     * Checks a method AST for required properties.
     * @param aAST the AST to check.
     * @param aPrefix prefix of checked names.
     * @param aException name of required exception.
     */
    private void checkMethod(DetailAST aAST, String aPrefix, String aException)
    {
        final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
        final String name = nameAST.getText();
        if (name.startsWith(aPrefix)) {
            if (!Utils.isPublic(aAST)) {
                log(nameAST.getLineNo(), nameAST.getColumnNo(),
                    "nonpublic.bean", "Method " + name);
            }
            if (Utils.isVoid(aAST)) {
                log(nameAST.getLineNo(), nameAST.getColumnNo(),
                    "voidmethod.bean", name);
            }
            if (!Utils
                .hasThrows(aAST, aException))
            {
                log(nameAST.getLineNo(), nameAST.getColumnNo(),
                    "missingthrows.bean",
                    new Object[] {name, aException});
            }
        }
    }

    /**
     * Checks that every method of an AST has a throws clause for a given
     * Exception.
     * @param aAST the AST to check.
     * @param aException the name of the Exception class.
     */
    protected void checkThrows(DetailAST aAST, String aException)
    {
        final DetailAST objBlock = aAST.findFirstToken(TokenTypes.OBJBLOCK);
        if (objBlock != null) {
            DetailAST child = (DetailAST) objBlock.getFirstChild();
            while (child != null) {
                if (child.getType() == TokenTypes.METHOD_DEF) {
                    if (!Utils.hasThrows(child, aException)) {
                        final DetailAST nameAST =
                            child.findFirstToken(TokenTypes.IDENT);
                        final String name = nameAST.getText();
                        log(nameAST.getLineNo(), nameAST.getColumnNo(),
                            "missingthrows.bean",
                             new Object[] {name, aException});
                    }
                }
                child = (DetailAST) child.getNextSibling();
            }
        }
    }
    
    /**
     * Checks that an AST contains the definition of a findByPrimaryKey
     * method.
     * @param aAST the AST to check.
     */
    protected void checkFindByPrimaryKey(DetailAST aAST)
    {
        if (!Utils.hasPublicMethod(aAST, "findByPrimaryKey", false, 1))
        {
            final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
            log(
                aAST.getLineNo(),
                nameAST.getColumnNo(),
                "missingmethod.bean",
                new Object[] {"Home interface", "findByPrimaryKey"});
        }
    }
}
