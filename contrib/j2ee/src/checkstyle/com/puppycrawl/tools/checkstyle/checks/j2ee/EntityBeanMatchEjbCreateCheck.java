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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks that every ejbCreate&lt;METHOD&gt; method has a
 * matching ejbPostCreate&lt;METHOD&gt; method.
 * Reference: Enterprise JavaBeansTM Specification,Version 2.1, section 10.6.5.
 * @author Rick Giles
 */
public class EntityBeanMatchEjbCreateCheck
    extends AbstractBeanCheck
{
    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        if (Utils.hasImplements(aAST, "javax.ejb.EntityBean")) {

            // determine sets of ejbCreate<METHOD> and ejbPostCreate<METHOD>
            final Set ejbCreates = new HashSet();
            final Set ejbPostCreates = new HashSet();
            final DetailAST objBlock = aAST.findFirstToken(TokenTypes.OBJBLOCK);
            if (objBlock != null) {
                DetailAST child = (DetailAST) objBlock.getFirstChild();
                while (child != null) {
                    if (child.getType() == TokenTypes.METHOD_DEF) {
                        final DetailAST nameNode =
                            child.findFirstToken(TokenTypes.IDENT);
                        final String name = nameNode.getText();
                        if (name.startsWith("ejbCreate")) {
                            ejbCreates.add(child);
                        }
                        if (name.startsWith("ejbPostCreate")) {
                            ejbPostCreates.add(child);
                        }
                    }
                    child = (DetailAST) child.getNextSibling();
                }
            }

            final Iterator it = ejbCreates.iterator();
            while (it.hasNext()) {
                final DetailAST createMethod = (DetailAST) it.next();
                final DetailAST nameAST =
                    createMethod.findFirstToken(TokenTypes.IDENT);
                final String name = nameAST.getText();
                final String method = name.substring("ejbCreate".length());

                // search for matching ejbPostCreate;
                boolean match = false;
                final Iterator itPostCreate = ejbPostCreates.iterator();
                while (!match && itPostCreate.hasNext()) {
                    final DetailAST postCreateMethod =
                        (DetailAST) itPostCreate.next();
                    final DetailAST postCreateNameAST =
                        postCreateMethod.findFirstToken(TokenTypes.IDENT);
                    final String postCreateName = postCreateNameAST.getText();
                    if (!postCreateName.equals("ejbPostCreate" + method)) {
                        continue;
                    }
                    match =
                        Utils.sameParameters(createMethod, postCreateMethod);
                }
                if (!match) {
                    log(nameAST.getLineNo(), nameAST.getColumnNo(),
                        "unmatchedejbcreate.bean", name);
                }
            }
        }
    }
}
