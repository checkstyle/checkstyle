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

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AnnotationUtility;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.Set;

/**
 * <p>
 * Throwing java.lang.Error or java.lang.RuntimeException
 * is almost never acceptable.
 * </p>
 * Check has following properties:
 * <p>
 * <b>illegalClassNames</b> - throw class names to reject.
 * </p>
 * <p>
 * <b>ignoredMethodNames</b> - names of methods to ignore.
 * </p>
 * <p>
 * <b>ignoreOverridenMethods</b> - ignore checking overriden methods (marked with Override
 *  or java.lang.Override annotation) default value is <b>true</b>.
 * </p>
 *
 * @author Oliver Burn
 * @author John Sirois
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 */
public final class IllegalThrowsCheck extends AbstractIllegalCheck
{

    /** Default ignored method names. */
    private static final String[] DEFAULT_IGNORED_METHOD_NAMES = {
        "finalize",
    };

    /** property for ignoring overriden methods. */
    private boolean mIgnoreOverridenMethods = true;

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
        final DetailAST methodDef = aDetailAST.getParent();
        DetailAST token = aDetailAST.getFirstChild();
        // Check if the method with the given name should be ignored.
        if (!isIgnorableMethod(methodDef)) {
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
     * Checks if current method is ignorable due to Check's properties.
     * @param aMethodDef {@link TokenTypes#METHOD_DEF METHOD_DEF}
     * @return true if method is ignorable.
     */
    private boolean isIgnorableMethod(DetailAST aMethodDef)
    {
        return shouldIgnoreMethod(aMethodDef.findFirstToken(TokenTypes.IDENT).getText())
            || mIgnoreOverridenMethods
               && (AnnotationUtility.containsAnnotation(aMethodDef, "Override")
                  || AnnotationUtility.containsAnnotation(aMethodDef, "java.lang.Override"));
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

    /**
     * Sets <b>ignoreOverridenMethods</b> property value.
     * @param aIgnoreOverridenMethods Check's property.
     */
    public void setIgnoreOverridenMethods(boolean aIgnoreOverridenMethods)
    {
        this.mIgnoreOverridenMethods = aIgnoreOverridenMethods;
    }
}
