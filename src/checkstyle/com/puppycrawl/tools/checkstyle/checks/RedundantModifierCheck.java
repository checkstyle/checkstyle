////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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

import java.util.Stack;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.JavaTokenTypes;

// TODO: need to create a class to represent the constants in JavaTokenTypes.
// Needed to break circular dependencies
public class RedundantModifierCheck extends Check implements JavaTokenTypes
{
    /** tracks if in an interface */
    private final Stack mInInterface = new Stack();

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree()
    {
        super.beginTree();
        mInInterface.clear();
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {MODIFIERS, INTERFACE_DEF, CLASS_DEF};
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case INTERFACE_DEF:
            mInInterface.push(Boolean.TRUE);
            break;
        case CLASS_DEF:
            mInInterface.push(Boolean.FALSE);
            break;
        case MODIFIERS:

            // modifiers of the interface itself (public interface X)
            // will be below the INTERFACE_DEF node. Example:

            // public interface X {void y();}

            // INTERFACE_DEF
            // + MODUFIERS
            //   + public
            // + OBJ_BLOCK
            //   + ...

            if (inInterfaceBlock(aAST)) {
                DetailAST ast = (DetailAST) aAST.getFirstChild();
                while (ast != null) {
                    String modifier = ast.getText();
                    if ("public".equals(modifier)
                        || "abstract".equals(modifier))
                    {
                        log(ast.getLineNo(),
                            ast.getColumnNo(),
                            "redundantModifier",
                            new String[] {modifier});
                    }
                    ast = (DetailAST) ast.getNextSibling();
                }
            }
            break;
        default:
            return;
        }
    }

    /** @return whether currently in an interface block,
     *          i.e. in an OBJ_BLOCK of an INTERFACE_DEF
     */
    private boolean inInterfaceBlock(DetailAST aAST)
    {
        if (mInInterface.empty()) {
            return false;
        }
        if (aAST.getParent().getType() == INTERFACE_DEF) {
            int size = mInInterface.size();
            return size > 1 && Boolean.TRUE.equals(mInInterface.get(size - 2));
        }
        else {
            return Boolean.TRUE.equals(mInInterface.peek());
        }
    }

}
