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

import com.puppycrawl.tools.checkstyle.checks.ImportCheck;
import com.puppycrawl.tools.checkstyle.JavaTokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

/**
 * Checks for unused import statements.
 */
public class UnusedImportsCheck extends ImportCheck
{
    private boolean mCollect;
    private final Set mImports = new HashSet();
    private final Set mReferenced = new HashSet();

    public void beginTree()
    {
        mCollect = false;
        mImports.clear();
        mReferenced.clear();
    }

    public void finishTree()
    {
        // loop over all the imports to see if referenced.
        final Iterator it = mImports.iterator();
        while (it.hasNext()) {
            final String imp = (String) it.next();
            if (!mReferenced.contains(basename(imp))) {
                log(666, "unused import " + imp);
            }
        }
    }

    public int[] getDefaultTokens()
    {
        return new int[] {JavaTokenTypes.IMPORT,
                          JavaTokenTypes.CLASS_DEF,
                          JavaTokenTypes.INTERFACE_DEF,
                          JavaTokenTypes.IDENT};
    }

    public void visitToken(DetailAST aAST)
    {
        if (aAST.getType() == JavaTokenTypes.IDENT) {
            if (mCollect) {
                processIdent(aAST);
            }
        }
        else if (aAST.getType() == JavaTokenTypes.IMPORT) {
            processImport(aAST);
        }
        else if ((aAST.getType() == JavaTokenTypes.CLASS_DEF)
            || (aAST.getType() == JavaTokenTypes.INTERFACE_DEF))
        {
            mCollect = true;
        }
    }

    private void processIdent(DetailAST aAST)
    {
        // TODO: should be a lot smarter in selection. Currently use
        // same algorithm as real checkstyle
        final DetailAST parent = aAST.getParent();
        if (parent.getType() == JavaTokenTypes.DOT) {
            if (aAST.getNextSibling() != null) {
                mReferenced.add(aAST.getText());
            }
        }
        else {
            mReferenced.add(aAST.getText());
        }
    }

    private void processImport(DetailAST aAST)
    {
        final String name = getImportText(aAST);
        if ((name != null) && !name.endsWith(".*")) {
            mImports.add(name);
        }
    }

    /**
     * @return the class name from a fully qualified name
     * @param aType the fully qualified name
     */
    private String basename(String aType)
    {
        final int i = aType.lastIndexOf(".");
        return (i == -1) ? aType : aType.substring(i + 1);
    }
}
