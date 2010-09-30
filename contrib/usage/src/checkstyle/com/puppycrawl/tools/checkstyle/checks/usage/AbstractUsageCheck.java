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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;
import com.puppycrawl.tools.checkstyle.checks.usage.transmogrify.ASTManager;
import com.puppycrawl.tools.checkstyle.checks.usage.transmogrify.ClassManager;
import com.puppycrawl.tools.checkstyle.checks.usage.transmogrify.Definition;
import com.puppycrawl.tools.checkstyle.checks.usage.transmogrify.SymTabAST;
import com.puppycrawl.tools.checkstyle.checks.usage.transmogrify.
    SymbolTableException;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.beanutils.ConversionException;

/**
 * Performs a usage check for fields, methods, parameters, variables.
 * @author Rick Giles
 */
public abstract class AbstractUsageCheck
    extends Check
{
    /** the regexp to match against */
    private Pattern mRegexp;
    /** the format string of the regexp */
    private String mIgnoreFormat;

    /**
     * Constructs an <code>AbstractUsageCheck</code>.
     */
    public AbstractUsageCheck()
    {
        setIgnoreFormat("^$");
    }

    /**
     * Set the ignore format to the specified regular expression.
     * @param aFormat a <code>String</code> value
     * @throws ConversionException unable to parse aFormat
     */
    public void setIgnoreFormat(String aFormat)
        throws ConversionException
    {
        try {
            mRegexp = Utils.getPattern(aFormat);
            mIgnoreFormat = aFormat;
        }
        catch (PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }
    }

    /** @return the regexp to match against */
    public Pattern getRegexp()
    {
        return mRegexp;
    }

    /** @return the regexp format */
    public String getIgnoreFormat()
    {
        return mIgnoreFormat;
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree(DetailAST aRootAST)
    {
        // use my class loader
        ClassManager.setClassLoader(getClassLoader());

        final String fileName = getFileContents().getFilename();
        getASTManager().addTree(fileName, aRootAST);
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        if (mustCheckReferenceCount(aAST)) {
            final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
            Pattern regexp = getRegexp();
            if ((regexp == null)
                || !regexp.matcher(nameAST.getText()).find())
            {
                getASTManager().registerCheckNode(this, nameAST);
            }
        }
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void finishTree(DetailAST aAST)
    {
        if (aAST == null) {
            // we have nothing to check
            return;
        }
        try {
            final Set nodes = getASTManager().getCheckNodes(this);
            if (nodes != null) {
                applyTo(nodes);
            }
        }
        catch (SymbolTableException ste) {
            logError(ste);
        }
        getASTManager().removeCheck(this);
    }

    /**
     * Logs an exception.
     * @param aException the exception to log.
     */
    public void logError(Exception aException)
    {
        log(0, "general.exception", new String[] {aException.getMessage()});
        Utils.getExceptionLogger().debug("An exception occured.", aException);
    }

    /**
     * Determines the reference count for a DetailAST.
     * @param aAST the DetailAST to count.
     * @return the number of references to aAST.
     */
    private int getReferenceCount(DetailAST aAST)
    {
        final SymTabAST ident = getASTManager().get(aAST);
        if (ident == null) {
            return 0;
        }
        final Definition definition =
            (Definition) ident.getDefinition();
        if (definition != null) {
            return definition.getNumReferences();
        }

        return 0;
    }

    /**
     * Returns the key for the Checkstyle error message.
     * @return the key for the Checkstyle error message.
     */
    public abstract String getErrorKey();

    /**
     * Determines whether the reference count of an aAST is required.
     * @param aAST the node to check.
     * @return true if the reference count of aAST is required.
     */
    public abstract boolean mustCheckReferenceCount(DetailAST aAST);

    /**
     * Applies this check to a set of nodes.
     * @param aNodes the nodes to check.
     */
    public void applyTo(Set aNodes)
    {
        final Iterator it = aNodes.iterator();
        while (it.hasNext()) {
            final DetailAST nameAST = (DetailAST) it.next();
            if (getReferenceCount(nameAST) == 1) {
                log(
                    nameAST.getLineNo(),
                    nameAST.getColumnNo(),
                    getErrorKey(),
                    nameAST.getText());
            }
        }
    }

    /**
     * Gets the manager for AST nodes.
     *  @return the AST manager.
     */
    protected ASTManager getASTManager()
    {
        return ASTManager.getInstance();
    }
}
