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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks that a MessageBean implementation satisfies MessageBean
 * requirements:
 * <ul>
 * <li>The class is defined as <code>public</code>.</li>
 * <li>The class cannot be defined as <code>abstract</code> or
 * <code>final</code>.</li>
 * <li>It implements one ejbCreate method.</li>
 * <li>It contains a <code>public</code> constructor with no parameters.</li>
 * <li>It must not define the <code>finalize</code> method.</li>
 * </ul>
 * Reference: Enterprise JavaBeansTM Specification,Version 2.1, section 15.7.2
 * http://java.sun.com/j2ee/tutorial/1_3-fcs/doc/MDB4.html#69040
 * @author Rick Giles
 */
public class MessageBeanCheck
    extends AbstractBeanCheck
{
    /**
     * @see com.puppycrawl.tools.checkstyle.api.Check
     */
    public void visitToken(DetailAST aAST)
    {
        if (Utils.hasImplements(aAST, "javax.ejb.MessageDrivenBean")
            && Utils.hasImplements(aAST, "javax.jms.MessageListener"))
        {
            checkBean(aAST, "Message bean", false);
            if (!hasRequiredEjbCreate(aAST)) {
                final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
                log(nameAST.getLineNo(), nameAST.getColumnNo(),
                    "missingmethod.bean",
                    new Object[] {"Message bean", "ejbCreate"});
            }
        }
    }

    /**
     * Determines whether an AST node for a MessageBean class definition
     * contains the required ejbCreate method.
     * @param aAST the class to check.
     * @return true if aAST contains the required ejbCreate method.
     */
    private boolean hasRequiredEjbCreate(DetailAST aAST)
    {
        final DetailAST objBlock = aAST.findFirstToken(TokenTypes.OBJBLOCK);
        if (objBlock != null) {
            DetailAST child = (DetailAST) objBlock.getFirstChild();
            while (child != null) {
                if (child.getType() == TokenTypes.METHOD_DEF) {
                    final DetailAST nameNode =
                        child.findFirstToken(TokenTypes.IDENT);
                    final String name = nameNode.getText();
                    if (name.equals("ejbCreate")) {
                        final DetailAST parameters =
                            child.findFirstToken(TokenTypes.PARAMETERS);
                        //TODO: actually check throws clause
                        final boolean validThrows = true;
                        if (Utils.isPublic(child)
                            && Utils.isVoid(child)
                            && !Utils.isStatic(child)
                            && !Utils.isFinal(child)
                            && (parameters.getChildCount() == 0)
                            && validThrows)
                        {
                            return true;
                        }
                    }
                }
                child = (DetailAST) child.getNextSibling();
            }
        }
        return false;
    }
}
