////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2004  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 * Checks for unused import statements.
 * </p>
 *  <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="UnusedImports"/&gt;
 * </pre>
 *
 * Compatible with Java 1.5 source.
 *
 * @author Oliver Burn
 * @version 1.0
 */
public class UnusedImportsCheck
    extends Check
{
    /** flag to indicate when time to start collecting references */
    private boolean mCollect;
    /** set of the imports */
    private final Set mImports = new HashSet();
    /** set of references - possibly to imports or other things */
    private final Set mReferenced = new HashSet();

    /** Default constructor. */
    public UnusedImportsCheck()
    {
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree(DetailAST aRootAST)
    {
        mCollect = false;
        mImports.clear();
        mReferenced.clear();
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void finishTree(DetailAST aRootAST)
    {
        // loop over all the imports to see if referenced.
        final Iterator it = mImports.iterator();
        while (it.hasNext()) {
            final FullIdent imp = (FullIdent) it.next();

            if (!mReferenced.contains(Utils.baseClassname(imp.getText()))) {
                log(imp.getLineNo(),
                    imp.getColumnNo(),
                    "import.unused", imp.getText());
            }
        }
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.IDENT,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.IDENT) {
            if (mCollect) {
                processIdent(aAST);
            }
        }
        else if (aAST.getType() == TokenTypes.IMPORT) {
            processImport(aAST);
        }
        else if (aAST.getType() == TokenTypes.STATIC_IMPORT) {
            processStaticImport(aAST);
        }
        else if ((aAST.getType() == TokenTypes.CLASS_DEF)
            || (aAST.getType() == TokenTypes.INTERFACE_DEF))
        {
            mCollect = true;
        }
    }

    /**
     * Collects references made by IDENT.
     * @param aAST the IDENT node to process
     */
    private void processIdent(DetailAST aAST)
    {
        // TODO: should be a lot smarter in selection. Currently use
        // same algorithm as real checkstyle
        final DetailAST parent = aAST.getParent();
        if (parent.getType() == TokenTypes.DOT) {
            if (aAST.getNextSibling() != null) {
                mReferenced.add(aAST.getText());
            }
        }
        else {
            mReferenced.add(aAST.getText());
        }
    }

    /**
     * Collects the details of imports.
     * @param aAST node containing the import details
     */
    private void processImport(DetailAST aAST)
    {
        final FullIdent name = FullIdent.createFullIdentBelow(aAST);
        if ((name != null) && !name.getText().endsWith(".*")) {
            mImports.add(name);
        }
    }

    /**
     * Collects the details of static imports.
     * @param aAST node containing the static import details
     */
    private void processStaticImport(DetailAST aAST)
    {
        final FullIdent name =
            FullIdent.createFullIdent(
                (DetailAST) aAST.getFirstChild().getNextSibling());
        if ((name != null) && !name.getText().endsWith(".*")) {
            mImports.add(name);
        }
    }
}
