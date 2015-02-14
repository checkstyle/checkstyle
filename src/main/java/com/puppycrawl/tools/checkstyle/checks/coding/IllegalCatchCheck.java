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
package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.LinkedList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Catching java.lang.Exception, java.lang.Error or java.lang.RuntimeException
 * is almost never acceptable.
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author <a href="mailto:IliaDubinin91@gmail.com">Ilja Dubinin</a>
 */
public final class IllegalCatchCheck extends AbstractIllegalCheck
{
    /** Creates new instance of the check. */
    public IllegalCatchCheck()
    {
        super(new String[] {"Exception", "Error",
                            "RuntimeException", "Throwable",
                            "java.lang.Error",
                            "java.lang.Exception",
                            "java.lang.RuntimeException",
                            "java.lang.Throwable",
        });
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.LITERAL_CATCH};
    }

    @Override
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return new int[] {TokenTypes.LITERAL_CATCH};
    }

    @Override
    public void visitToken(DetailAST detailAST)
    {
        final DetailAST paradef =
            detailAST.findFirstToken(TokenTypes.PARAMETER_DEF);
        final DetailAST excTypeParent =
                paradef.findFirstToken(TokenTypes.TYPE);
        final List<DetailAST> excTypes = getAllExceptionTypes(excTypeParent);

        for (DetailAST excType : excTypes) {
            final FullIdent ident = FullIdent.createFullIdent(excType);

            if (isIllegalClassName(ident.getText())) {
                log(detailAST, "illegal.catch", ident.getText());
            }
        }
    }

    /**
     * Finds all exception types in current catch.
     * We need it till we can have few different exception types into one catch.
     * @param parentToken - parent node for types (TYPE or BOR)
     * @return list, that contains all exception types in current catch
     */
    public List<DetailAST> getAllExceptionTypes(DetailAST parentToken)
    {
        DetailAST currentNode = parentToken.getFirstChild();
        final List<DetailAST> exceptionTypes = new LinkedList<>();
        if (currentNode.getType() == TokenTypes.BOR) {
            exceptionTypes.addAll(getAllExceptionTypes(currentNode));
            currentNode = currentNode.getNextSibling();
            if (currentNode != null) {
                exceptionTypes.add(currentNode);
            }
        }
        else {
            exceptionTypes.add(currentNode);
            while ((currentNode = currentNode.getNextSibling()) != null) {
                exceptionTypes.add(currentNode);
            }
        }
        return exceptionTypes;
    }
}
