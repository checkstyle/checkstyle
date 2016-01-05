////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that {@code catch} parameter names conform to a format specified by the format property.
 * The format is a {@link java.util.regex.Pattern regular expression} and defaults to
 * <strong>^(e|t|ex|[a-z][a-z][a-zA-Z]+)$</strong>.
 * </p>
 * <p>
 * Default pattern has the following characteristic:
 * </p>
 * <ul>
 * <li>allows names beginning with two lowercase letters followed by at least one uppercase or
 * lowercase letter</li>
 * <li>allows {@code e} abbreviation (suitable for exceptions end errors)</li>
 * <li>allows {@code ex} abbreviation (suitable for exceptions)</li>
 * <li>allows {@code t} abbreviation (suitable for throwables)</li>
 * <li>prohibits numbered abbreviations like {@code e1} or {@code t2}</li>
 * <li>prohibits one letter prefixes like {@code pException}</li>
 * <li>prohibits two letter abbreviations like {@code ie} or {@code ee}</li>
 * <li>prohibits any other characters than letters</li>
 * </ul>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="CatchParameterName"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check for names that begin with a lower case letter,
 * followed by any letters or digits is:
 * </p>
 * <pre>
 * &lt;module name="CatchParameterName"&gt;
 *    &lt;property name="format" value="^[a-z][a-zA-Z0-9]+$"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Michal Kordas
 */
public class CatchParameterNameCheck extends AbstractNameCheck {

    /**
     * Creates a new {@code CatchParameterNameCheck} instance.
     */
    public CatchParameterNameCheck() {
        super("^(e|t|ex|[a-z][a-z][a-zA-Z]+)$");
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.PARAMETER_DEF};
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    protected boolean mustCheckName(DetailAST ast) {
        return ast.getParent().getType() == TokenTypes.LITERAL_CATCH;
    }
}
