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
package com.puppycrawl.tools.checkstyle.checks.xpath;

import java.util.Iterator;

import org.jaxen.BaseXPath;
import org.jaxen.JaxenException;
import org.jaxen.XPath;

import antlr.ASTFactory;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks for an XPath in the root AST. Path elements are named
 * according to token types. Attributes of an element are bean
 * properties.
 * Requires jaxen, http://jaxen.sourceforge.net and
 * saxpath, http://sourceforge.net/projects/saxpath/.
 * Idea shamelessly stolen from the equivalent PMD rule (pmd.sourceforge.net).
 * @author Rick Giles
 */
public class XPathCheck extends Check
{
    /** XPath for this check */
    private XPath mXPath;

    /** error message */
    private String mMessage = "illegal.xpath";

    /** @see com.puppycrawl.tools.checkstyle.api.Check#getDefaultTokens() */
    public int[] getDefaultTokens()
    {
        return new int[0];
    }

    /**  @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree(DetailAST aAST)
    {
        if (mXPath != null) {
            final ASTFactory factory = new ASTFactory();
            factory.setASTNodeType(DetailAST.class.getName());
            // TODO: Need to resolve if need a fake root node....
            final DetailAST root =
                (DetailAST) factory.create(TokenTypes.EOF, "ROOT");
            root.setFirstChild(aAST);
            try {
                final Iterator it = mXPath.selectNodes(aAST).iterator();
                while (it.hasNext()) {
                    final DetailAST node = (DetailAST) it.next();
                    log(
                        node.getLineNo(),
                        node.getColumnNo(),
                        mMessage,
                        new String[] {node.getText()});
                }
            }
            catch (JaxenException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the error message for this check.
     * @param aMessage error message for this check.
     */
    public void setMessage(String aMessage)
    {
        mMessage = aMessage;
    }

    /**
     * Sets the XPath for this check.
     * @param aXPath the XPath for this check.
     * @throws JaxenException if there is an error.
     */
    public void setXPath(String aXPath)
        throws JaxenException
    {
        mXPath = new BaseXPath(aXPath, new DocumentNavigator());
    }
}
