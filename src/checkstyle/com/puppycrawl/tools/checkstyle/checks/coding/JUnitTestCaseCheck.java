////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import com.puppycrawl.tools.checkstyle.checks.CheckUtils;

/**
 * Ensures that the setUp(), tearDown()methods are named correctly,
 * have no arguments, return void and are either public or protected.
 * Also ensures that suite() is named correctly, have no arguments, return
 * junit.framewotk.Test, public and static.
 *
 * Rationale: often times developers will misname one or more of these
 * methods and not realise that the method is not being called.
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 */
public final class JUnitTestCaseCheck extends Check
{
    /** <code>setUp()</code> method name. */
    private static final String SET_UP_METHOD_NAME = "setUp";
    /** <code>tearDown()</code> method name. */
    private static final String TEAR_DOWN_METHOD_NAME = "tearDown";
    /** <code>suite()</code> method name. */
    private static final String SUITE_METHOD_NAME = "suite";

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.METHOD_DEF};
    }

    @Override
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.METHOD_DEF:
            visitMethodDef(aAST);
            break;
        default:
            throw new IllegalStateException(aAST.toString());
        }
    }

    /**
     * Checks given method definition.
     * @param aAST a method def node for check
     */
    private void visitMethodDef(DetailAST aAST)
    {
        final String name = aAST.findFirstToken(TokenTypes.IDENT).getText();

        if (name.equalsIgnoreCase(SET_UP_METHOD_NAME)) {
            checkSetUpTearDownMethod(aAST, name, SET_UP_METHOD_NAME);
        }
        else if (name.equalsIgnoreCase(TEAR_DOWN_METHOD_NAME)) {
            checkSetUpTearDownMethod(aAST, name, TEAR_DOWN_METHOD_NAME);
        }
        else if (name.equalsIgnoreCase(SUITE_METHOD_NAME)) {
            checkSuiteMethod(aAST, name);
        }
    }

    /**
     * Checks signature/name of <code>suite()</code>.
     * @param aAST method definition node
     * @param aActualName method name
     */
    private void checkSuiteMethod(DetailAST aAST, String aActualName)
    {
        if (!aActualName.equals(SUITE_METHOD_NAME)) {
            log(aAST, "junit.method.name", SUITE_METHOD_NAME);
        }

        if (!isPublicAndStatic(aAST)) {
            log(aAST, "junit.method.public.and.static", SUITE_METHOD_NAME);
        }

        // let's check return type
        final DetailAST typeAST = aAST.findFirstToken(TokenTypes.TYPE);
        final boolean isArray =
            (typeAST.findFirstToken(TokenTypes.ARRAY_DECLARATOR) != null);
        final String type = CheckUtils.createFullType(typeAST).getText();
        if (isArray
            || (!"Test".equals(type)
            && !"junit.framework.Test".equals(type)))
        {
            log(aAST, "junit.method.return.type",
                SUITE_METHOD_NAME, "junit.framework.Test");
        }
        checkParameters(aAST, SUITE_METHOD_NAME);
    }

    /**
     * Checks signature/name of <code>setUp()</code>/<code>tearDown</code>.
     * @param aAST method definition node
     * @param aActualName actual method name
     * @param aExpectedName expected method name
     */
    private void checkSetUpTearDownMethod(DetailAST aAST, String aActualName,
                                          String aExpectedName)
    {
        if (!aActualName.equals(aExpectedName)) {
            log(aAST, "junit.method.name", aActualName, aExpectedName);
        }

        if (!isPublicOrProtected(aAST)) {
            log(aAST, "junit.method.protected.or.public", aExpectedName);
        }

        if (isStatic(aAST)) {
            log(aAST, "junit.method.static", aExpectedName);
        }

        checkReturnValue(aAST, aActualName);
        checkParameters(aAST, aActualName);
    }

    /**
     * Checks that given method returns <code>void</code>.
     * @param aAST method definition node
     * @param aName method name
     */
    private void checkReturnValue(DetailAST aAST, String aName)
    {
        final DetailAST returnValueAST = aAST.findFirstToken(TokenTypes.TYPE);

        if (returnValueAST.findFirstToken(TokenTypes.LITERAL_VOID) == null) {
            log(aAST, "junit.method.return.type", aName, "void");
        }
    }

    /**
     * Checks return value of given method.
     * @param aAST method definition node
     * @param aName method name
     */
    private void checkParameters(DetailAST aAST, String aName)
    {
        final DetailAST parametersAST =
            aAST.findFirstToken(TokenTypes.PARAMETERS);

        if (parametersAST.getChildCount() != 0) {
            log(aAST, "junit.method.parameters", aName);
        }
    }

    /**
     * Checks if given method declared as public or
     * protected and non-static.
     * @param aAST method definition node
     * @return true if given method is declared as public or protected
     */
    private boolean isPublicOrProtected(DetailAST aAST)
    {
        final DetailAST modifiersAST =
            aAST.findFirstToken(TokenTypes.MODIFIERS);
        final DetailAST publicAST =
            modifiersAST.findFirstToken(TokenTypes.LITERAL_PUBLIC);
        final DetailAST protectedAST =
            modifiersAST.findFirstToken(TokenTypes.LITERAL_PROTECTED);

        return (publicAST != null) || (protectedAST != null);
    }

    /**
     * Checks if given method declared as <code>public</code> and
     * <code>static</code>.
     * @param aAST method definition node
     * @return true if given method is declared as public and static
     */
    private boolean isPublicAndStatic(DetailAST aAST)
    {
        final DetailAST modifiersAST =
            aAST.findFirstToken(TokenTypes.MODIFIERS);
        final DetailAST publicAST =
            modifiersAST.findFirstToken(TokenTypes.LITERAL_PUBLIC);
        final DetailAST staticAST =
            modifiersAST.findFirstToken(TokenTypes.LITERAL_STATIC);

        return (publicAST != null) && (staticAST != null);
    }

    /**
     * Checks if given method declared as static.
     * @param aAST method definition node
     * @return true if given method is declared as static
     */
    private boolean isStatic(DetailAST aAST)
    {
        final DetailAST modifiersAST =
            aAST.findFirstToken(TokenTypes.MODIFIERS);
        final DetailAST staticAST =
            modifiersAST.findFirstToken(TokenTypes.LITERAL_STATIC);

        return (staticAST != null);
    }
}
