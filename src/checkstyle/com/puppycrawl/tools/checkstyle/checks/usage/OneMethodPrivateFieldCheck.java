////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.usage;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.usage.transmogrify.Definition;
import com.puppycrawl.tools.checkstyle.checks.usage.transmogrify.Reference;
import com.puppycrawl.tools.checkstyle.checks.usage.transmogrify.SymTabAST;


/**
 * <p>Checks that a private field is used in more than one method,
 * constructor, or initializer.
 * </p>
 * <p>
 * Rationale: a private field used in only one method, constructor, or
 * initializer should be replaced by a local variable.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="usage.OneMethodPrivateField"/&gt;
 * </pre>
 *
 * @author Rick Giles
 */
public class OneMethodPrivateFieldCheck
    extends AbstractUsageCheck
{
    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.checks.usage.AbstractUsageCheck */
    public String getErrorKey()
    {
        return "one.method.private.field";
    }

    /** @see com.puppycrawl.tools.checkstyle.checks.usage.AbstractUsageCheck */
    public boolean mustCheckReferenceCount(DetailAST aAST)
    {
        final DetailAST mods = aAST.findFirstToken(TokenTypes.MODIFIERS);
        return ((mods != null)
            && (ScopeUtils.getScopeFromMods(mods) == Scope.PRIVATE));
    }

    /** @see com.puppycrawl.tools.checkstyle.checks.usage.AbstractUsageCheck */
    public void applyTo(Set aNodes)
    {
        // apply the check to each private field
        final Set methods = new HashSet();
        final Iterator it = aNodes.iterator();
        while (it.hasNext()) {
            methods.clear();
            final DetailAST nameAST = (DetailAST) it.next();
            // find methods using the field
            final Iterator refIt = getReferences(nameAST);
            while (refIt.hasNext()) {
                final Reference ref = (Reference) refIt.next();
                final SymTabAST refNode = ref.getTreeNode();
                final DetailAST refDetail = refNode.getDetailNode();
                // don't need to check a self-reference
                if (refDetail == nameAST) {
                    continue;
                }
                DetailAST parent = refDetail.getParent();
                while (parent != null) {
                    final int type = parent.getType();
                    if ((type == TokenTypes.METHOD_DEF)
                        || (type == TokenTypes.CTOR_DEF)
                        || (type == TokenTypes.INSTANCE_INIT)
                        || (type == TokenTypes.STATIC_INIT))
                    {
                        methods.add(parent);
                        break;
                    }
                    // initializer for inner class?
                    else if (type == TokenTypes.CLASS_DEF) {
                        break;
                    }
                    parent = parent.getParent();
                }
            }
            if (methods.size() == 1) {
                log(
                    nameAST.getLineNo(),
                    nameAST.getColumnNo(),
                    getErrorKey(),
                    nameAST.getText());
            }
        }
    }

    /**
     * Returns the references to an AST.
     * @param aAST the AST for the references.
     * @return an iterator for the references to aAST.
     */
    private Iterator getReferences(DetailAST aAST)
    {
        final SymTabAST ident = getASTManager().get(aAST);
        final Definition definition =
            (Definition) ident.getDefinition();
        if (definition != null) {
            return definition.getReferences();
        }
        final Set dummy = new HashSet();
        return dummy.iterator();
    }
}
