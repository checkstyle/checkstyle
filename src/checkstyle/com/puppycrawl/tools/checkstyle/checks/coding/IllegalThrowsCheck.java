////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.Set;

/**
 * Throwing java.lang.Error or java.lang.RuntimeException
 * is almost never acceptable.
 * @author Oliver Burn
 */
public final class IllegalThrowsCheck extends AbstractIllegalCheck
{

    /** Default ignored method names. */
    private static final String[] DEFAULT_IGNORED_METHOD_NAMES = {
        "finalize",
    };

    /** methods which should be ignored. */
    private final Set<String> mIgnoredMethodNames = Sets.newHashSet();

    /** Creates new instance of the check. */
    public IllegalThrowsCheck()
    {
        super(new String[] {"Error",
                            "RuntimeException", "Throwable",
                            "java.lang.Error",
                            "java.lang.RuntimeException",
                            "java.lang.Throwable",
        });
        setIgnoredMethodNames(DEFAULT_IGNORED_METHOD_NAMES);
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.LITERAL_THROWS};
    }

    @Override
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST aDetailAST)
    {
        DetailAST token = aDetailAST.getFirstChild();
        // Check if the method with the given name should be ignored.
        if (!(shouldIgnoreMethod(aDetailAST.getParent().findFirstToken(
                                     TokenTypes.IDENT).getText())))
        {
            while (token != null) {
                if (token.getType() != TokenTypes.COMMA) {
                    final FullIdent ident = FullIdent.createFullIdent(token);
                    if (isIllegalClassName(ident.getText())) {
                        log(token, "illegal.throw", ident.getText());
                    }
                }
                token = token.getNextSibling();
            }
        }
    }

    /**
     * Check if the method is specified in the ignore method list
     * @param aName the name to check
     * @return whether the method with the passed name should be ignored
     */
    private boolean shouldIgnoreMethod(String aName)
    {
        return mIgnoredMethodNames.contains(aName);
    }

    /**
     * Set the list of ignore method names.
     * @param aMethodNames array of ignored method names
     */
    public void setIgnoredMethodNames(String[] aMethodNames)
    {
        mIgnoredMethodNames.clear();
        for (String element : aMethodNames) {
            mIgnoredMethodNames.add(element);
        }
    }
}
